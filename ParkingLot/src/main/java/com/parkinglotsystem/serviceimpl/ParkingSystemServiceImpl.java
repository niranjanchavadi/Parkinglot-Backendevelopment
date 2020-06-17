package com.parkinglotsystem.serviceimpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.parkinglotsystem.exception.ParkingSystemException;
import com.parkinglotsystem.mailservice.JavaMailservices;
import com.parkinglotsystem.model.Gate;
import com.parkinglotsystem.model.ParkingLot;
import com.parkinglotsystem.model.ParkingLotSystem;
import com.parkinglotsystem.model.Slot;
import com.parkinglotsystem.model.Vehicle;
import com.parkinglotsystem.model.VehicleEntry;
import com.parkinglotsystem.model.VehicleInfo;
import com.parkinglotsystem.repository.GateRepository;
import com.parkinglotsystem.repository.ParkingLotReposiory;
import com.parkinglotsystem.repository.SlotRepository;
import com.parkinglotsystem.repository.UserRepo;
import com.parkinglotsystem.repository.VehicleEntryRepository;
import com.parkinglotsystem.repository.VehicleInfoRepository;
import com.parkinglotsystem.repository.VehicleRepository;
import com.parkinglotsystem.response.ParkingSystemResponse;
import com.parkinglotsystem.response.Response;
import com.parkinglotsystem.service.ParkingSystemService;
import com.parkinglotsystem.util.Util;

@Service
@Transactional
public class ParkingSystemServiceImpl implements ParkingSystemService {

	@Autowired
	ParkingLotReposiory parkingLotrepository;

	@Autowired
	SlotRepository slotrepository;

	@Autowired
	GateRepository gaterepository;

	@Autowired
	VehicleRepository vehiclerepository;

	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private Environment env;
	
	@Autowired
	VehicleInfoRepository vehicleInforepository;

	@Autowired
	VehicleEntryRepository vehicleEntryrepository;
	
	@Autowired
	private JavaMailservices messageService;
	

	@Override
	public void saveParkingLot(ParkingLotSystem parkingLot) {
		this.parkingLotrepository.save(parkingLot);
	}

	@Override
	public Optional<ParkingLotSystem> getParkingLot() {
		return Optional.ofNullable(this.parkingLotrepository.findAll().get(0));
	}

	@Override
	public Optional<Slot> getSlot(Long id) {
		return this.slotrepository.findById(id);
	}

	@Override
	public Optional<Gate> getGate(Long id) {
		return this.gaterepository.findById(id);
	}

	@Override
	public void saveVehicleEntryFlow(Vehicle vehicle, Gate gate, Slot slot) {
		if (vehicle.getNumber().isEmpty()) {
			throw new ParkingSystemException("Vehicle number can not be empty !");
		}
		if (vehicle.getType() == null) {
			throw new ParkingSystemException("Vehicle type can not be null !");
		}

		VehicleInfo vehicleInfo = new VehicleInfo();
		vehicleInfo.setInTime(new Date(System.currentTimeMillis()));
		vehicleInfo.setEntryGate(gate);
		vehicle.setInfo(vehicleInfo);
		saveVehicle(vehicle);
		slot.setIsFree(false);
		slot.setVehicle(vehicle);
		this.slotrepository.save(slot);
	}

	@Override
	public void saveVehicleExitFlow(Vehicle vehicle, Gate exitGate, Slot slot, Short amount) {
		Optional<Slot> optSlot = this.slotrepository.findById(slot.getId());
		Optional<Vehicle> optVehicle = this.vehiclerepository.findById(vehicle.getId());
		if (optVehicle.isPresent()) {
			Vehicle savedVehicle = optVehicle.get();
			VehicleInfo vehicleInfo = savedVehicle.getInfo();
			vehicleInfo.setOutTime(new Date(System.currentTimeMillis()));
			vehicleInfo.setExitGate(exitGate);
			vehicleInfo.setAmount(amount);
			this.vehicleInforepository.save(vehicleInfo);
			VehicleEntry entry = new VehicleEntry();
			entry.setEntryGate(vehicleInfo.getEntryGate());
			entry.setExitGate(exitGate);
			entry.setInTime(vehicleInfo.getInTime());
			entry.setOutTime(vehicleInfo.getOutTime());
			entry.setSlot(slot);
			entry.setVehicle(savedVehicle);
			this.vehicleEntryrepository.save(entry);
			Slot savedSlot = optSlot.get();
			savedSlot.setIsFree(true);
			savedSlot.setVehicle(null);
			this.slotrepository.save(savedSlot);
		} else {
			throw new ParkingSystemException("Vehicle entry does not exist in system !");
		}
	}

	@Override
	public Optional<List<VehicleEntry>> getAllVehicleEntries() {
		return Optional.ofNullable(this.vehicleEntryrepository.findAll());
	}

	private void saveVehicle(Vehicle vehicle) {
		this.vehiclerepository.save(vehicle);
	}
	
	@Override
	public ResponseEntity<ParkingSystemResponse> createVehicleEntryFlow(Vehicle vehicle) {
		ParkingSystemResponse psRes = null;
		HttpStatus status = null;
		if(slotrepository.checkFreeSlots() <= 0L) {
			psRes = new ParkingSystemResponse("Parking Slot is not free");
			status = HttpStatus.CONFLICT;
		} 
		else {
			long slotId = slotrepository.findNearestSlot();
			Optional<Slot> optSlot = this.getSlot(slotId);

			if (optSlot.isPresent()) {
				if (optSlot.get().getIsFree()) {
					long gateId = slotrepository.findGateIdBySlot(slotId);
					Optional<Gate> optGate = this.getGate(gateId);
					if (optGate.isPresent()) {
						this.saveVehicleEntryFlow(vehicle, optGate.get(), optSlot.get());
						psRes = new ParkingSystemResponse("Vehicle entry created");
						status = HttpStatus.CREATED;
					} else {
						psRes = new ParkingSystemResponse("Invalid gate, Could not locate entry gate");
						status = HttpStatus.NOT_FOUND;
					}
				} else {
					psRes = new ParkingSystemResponse("Parking Slot is not free");
					status = HttpStatus.CONFLICT;
				}
			} else {
				psRes = new ParkingSystemResponse("Invalid slot, Could not locate slot for Vehicle");
				status = HttpStatus.NOT_FOUND;
			
		}
}
		return new ResponseEntity<ParkingSystemResponse>(psRes, status);
	}
	
	
	@Override
	public ResponseEntity<ParkingSystemResponse> createVehicleExitFlow(Vehicle vehicle) {
		long vehicleId = vehiclerepository.findVehicleIdByNumber(vehicle.getNumber());
		vehicle.setId(vehicleId);
		VehicleInfo vehicleInfo = vehicleInforepository.getVehicleInfoByVehicleId(vehicleId);
		long slotId = slotrepository.findSlotIdByVehicleId(vehicleId);
		short amount = computeamount(new Date(vehicleInfo.getInTime().getTime()), new Date(System.currentTimeMillis()));
		
		Optional<Slot> optSlot = this.getSlot(slotId);
		ParkingSystemResponse psRes = null;
		HttpStatus status = null;
		if (optSlot.isPresent()) {
			Optional<Gate> optGate = this.getGate(vehicleInfo.getEntryGate().getId());
			if (optGate.isPresent()) {
				this.saveVehicleExitFlow(vehicle, optGate.get(), optSlot.get(), amount);
				psRes = new ParkingSystemResponse("Vehicle exit flow created");
				status = HttpStatus.CREATED;
			} else {
				psRes = new ParkingSystemResponse("Invalid gate, Could not locate entry gate");
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			psRes = new ParkingSystemResponse("Invalid slot, Could not locate slot for Vehicle");
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<ParkingSystemResponse>(psRes, status);
	}
		
	@Override
	public void  createParkingLotSystem(int noOfSlots, int noOfGates) {
		ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
		Set<ParkingLot> parkingLots = new HashSet<>();
		Set<Gate> gates = new HashSet<>();
		for(int i = 0; i< noOfGates;i++) {
			Gate gate = new Gate();
			ParkingLot parkingLot = new ParkingLot();
			Set<Slot> slots = new HashSet<Slot>();
			
			for(int j = 0; j < noOfSlots;j++) {
				Slot slot = new Slot();
				slot.setIsFree(true);
				slots.add(slot);
			}
			gate.setNumber((short) (i+1));
			parkingLot.setNumber(gate.getNumber());
			parkingLot.setSlots(slots);

			parkingLots.add(parkingLot);
			gates.add(gate);

		}
		parkingLotSystem.setSections(parkingLots);
		parkingLotSystem.setGates(gates);
		
		this.saveParkingLot(parkingLotSystem);
	}
	
	
	private short computeamount(Date inTime, Date outTime) {
		long hours = (inTime.getTime() - outTime.getTime()) / (60 * 60 * 1000);
		return  (short) (hours > 0 ? hours * Util.HOURLY_RATE :  Util.HOURLY_RATE);
		
	}

	@Override
	public Response isParkinglotisfull() throws MessagingException {
	
		if(slotrepository.checkFreeSlots() <= 0L) {
			
			List<String> alList = new ArrayList<String>();
			userrepo.getallEmail();
			alList.addAll(userrepo.getallEmail());
			alList.stream()
            .map(String::toLowerCase)
            .forEach(System.out::println);
			String[] myArray = new String[alList.size()];
			alList.toArray(myArray);
			Address[] addresses = new InternetAddress[myArray.length];
			 for(int i=0; i<myArray.length; i++){
				 addresses[i] = new InternetAddress(myArray[i]);		       
		      }
			 messageService.send(myArray, Util.PARKING_LOT_IS_FULL, "Parking is Full!!!!!");
			 
			return new Response(env.getProperty("parkinglot.is.full"), env.getProperty("already.exist.exception.status"));
			
			
			
		} 
		
			
			return new Response(env.getProperty("valid.input.message"), env.getProperty("user.authentication.exception.status"),slotrepository.checkFreeSlots());
	}

}
	

		
