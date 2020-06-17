package com.parkinglotsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parkinglotsystem.model.VehicleInfo;

public interface VehicleInfoRepository extends JpaRepository<VehicleInfo, Long> {
	
	@Query(value = "Select info.* from parkinglotsystem.vehicle vehicle,parkinglotsystem.vehicleinfo info  \n" + 
			"where vehicle.vehicleinfo_id = info.id and vehicle.id = ? ", nativeQuery = true)
	VehicleInfo getVehicleInfoByVehicleId(long vehicleId);

}
