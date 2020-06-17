package com.parkinglotsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkinglotsystem.model.ParkingLotSystem;

public interface ParkingLotReposiory extends JpaRepository<ParkingLotSystem, Long> {

}