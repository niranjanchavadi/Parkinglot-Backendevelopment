package com.parkinglotsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parkinglotsystem.model.Slot;

public interface SlotRepository extends JpaRepository<Slot, Long> {
	
	@Query(value = "select min(id) from parkinglotsystem.slot where isfree = 1 ", nativeQuery = true)
	long findNearestSlot();
	
	@Query(value = "select slot_id from parkinglotsystem.slot where id = ?", nativeQuery = true)
	long findGateIdBySlot(long id);
	
	@Query(value = "select count(*) from parkinglotsystem.slot where isfree = 1 ", nativeQuery = true)
	long checkFreeSlots();
	
	@Query(value = "select id from parkinglotsystem.slot where vehicle_id = ? ", nativeQuery = true)
	long findSlotIdByVehicleId(long  vehicleId);

}
