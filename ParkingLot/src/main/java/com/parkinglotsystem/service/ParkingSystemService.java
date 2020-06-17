package com.parkinglotsystem.service;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.http.ResponseEntity;

import com.parkinglotsystem.model.Gate;
import com.parkinglotsystem.model.ParkingLotSystem;
import com.parkinglotsystem.model.Slot;
import com.parkinglotsystem.model.Vehicle;
import com.parkinglotsystem.model.VehicleEntry;
import com.parkinglotsystem.response.ParkingSystemResponse;
import com.parkinglotsystem.response.Response;

public interface ParkingSystemService {

	void saveParkingLot(ParkingLotSystem parkingLot);

	Optional<ParkingLotSystem> getParkingLot();

	Optional<Slot> getSlot(Long id);

	Optional<Gate> getGate(Long id);

	void saveVehicleEntryFlow(Vehicle vehicle, Gate gate, Slot slot);

	void saveVehicleExitFlow(Vehicle vehicle, Gate gate, Slot slot, Short amount);
	
	Optional<List<VehicleEntry>> getAllVehicleEntries();

	ResponseEntity<ParkingSystemResponse> createVehicleEntryFlow(Vehicle vehicle);

	ResponseEntity<ParkingSystemResponse> createVehicleExitFlow(Vehicle vehicle);

	void createParkingLotSystem(int slots, int gates);

	Response isParkinglotisfull() throws AddressException, MessagingException;
}
