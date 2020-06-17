package com.parkinglotsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkinglotsystem.model.VehicleEntry;

public interface VehicleEntryRepository extends JpaRepository<VehicleEntry, Long> {

}
