package com.parkinglotsystem.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.parkinglotsystem.enums.ActorType;
import com.parkinglotsystem.model.ParkingLotSystem;
import com.parkinglotsystem.model.Vehicle;
import com.parkinglotsystem.model.VehicleEntry;
import com.parkinglotsystem.response.ParkingSystemResponse;
import com.parkinglotsystem.response.Response;
import com.parkinglotsystem.service.ParkingSystemService;
import com.parkinglotsystem.serviceimpl.AuthService;

@RequestMapping(path = "parking-system")
@RestController
public class ParkingSystemControllers {

	@Autowired
	ParkingSystemService parkingLotService;
	
	@Autowired
	AuthService authService;


	@PostMapping("/parking-lot")
	public ResponseEntity<ParkingSystemResponse> createParkingLot(@RequestBody ParkingLotSystem parkingLot, 
			                                                      @RequestParam long userId) {
		ActorType actorType = authService.getActorType(userId);
		System.out.println("Inside  ParkingSystemControllers::createParkingLot  userId::"+userId
				+" actor type::"+actorType);
		if(actorType != null && actorType.equals(ActorType.OWNER)){
			this.parkingLotService.saveParkingLot(parkingLot);
			return new ResponseEntity<ParkingSystemResponse>(new ParkingSystemResponse("Parking Lot Created"),
					HttpStatus.CREATED);
		} else { 
			
			return new ResponseEntity<ParkingSystemResponse>(new ParkingSystemResponse("User Not Found/authorized "),
					HttpStatus.BAD_REQUEST);
		}

	}
	
	
	@PostMapping("/parkinglot")
	public ResponseEntity<ParkingSystemResponse> createParkingLotSystem(
			@RequestParam int slots,@RequestParam int gates,
		    @RequestParam long userId) {
		ActorType actorType = authService.getActorType(userId);
		System.out.println("Inside  ParkingSystemControllers::createParkingLot  userId::"+userId
				+" actor type::"+actorType);
		if(actorType != null && actorType.equals(ActorType.OWNER)){
			this.parkingLotService.createParkingLotSystem(slots, gates);
			return new ResponseEntity<ParkingSystemResponse>(new ParkingSystemResponse("Parking Lot Created"),
					HttpStatus.CREATED);
		} else { 
			
			return new ResponseEntity<ParkingSystemResponse>(new ParkingSystemResponse("User Not Found/authorized "),
					HttpStatus.BAD_REQUEST);
		}

	}
	

	@GetMapping("/parking-lot")
	public ResponseEntity<ParkingLotSystem> getParkingLot() {
		return new ResponseEntity<ParkingLotSystem>(this.parkingLotService.getParkingLot().get(), HttpStatus.OK);
	}

	@PostMapping("/park")
	public ResponseEntity<ParkingSystemResponse> createVehicleEntryFlow(@RequestBody Vehicle vehicle) {
		return this.parkingLotService.createVehicleEntryFlow(vehicle);
	}

	@PostMapping("/unpark")
	public ResponseEntity<ParkingSystemResponse> createVehicleExitFlow(@RequestBody Vehicle vehicle) {
		return this.parkingLotService.createVehicleExitFlow(vehicle);
	}

	@GetMapping("/allvehicles")
	public ResponseEntity<List<VehicleEntry>> getAllVehicleEntries() {
		Optional<List<VehicleEntry>> optListEntries = this.parkingLotService.getAllVehicleEntries();
		if (optListEntries.isPresent()) {
			return new ResponseEntity<List<VehicleEntry>>(optListEntries.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<VehicleEntry>>(new ArrayList<VehicleEntry>(), HttpStatus.NO_CONTENT);
		}
	}
	
          
	@GetMapping("/isParkingisfull")
	public ResponseEntity<Response> isParkingLotIsFull() throws AddressException, MessagingException {
		return  ResponseEntity.ok(parkingLotService.isParkinglotisfull());	}
	
	
}
