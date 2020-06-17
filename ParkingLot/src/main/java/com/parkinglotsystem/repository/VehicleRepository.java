package com.parkinglotsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parkinglotsystem.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	
	@Query(value = "select id from parkinglotsystem.vehicle where vehicle.number = ? ", nativeQuery = true)
	long findVehicleIdByNumber(String vehicleNumber);

}
