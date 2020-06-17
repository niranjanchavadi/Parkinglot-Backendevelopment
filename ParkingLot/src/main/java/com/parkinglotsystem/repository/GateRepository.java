package com.parkinglotsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkinglotsystem.model.Gate;

public interface GateRepository extends JpaRepository<Gate, Long> {

}
