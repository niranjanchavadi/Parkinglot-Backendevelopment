package com.parkinglotsystem.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "parkinglotsystem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParkingLotSystem implements Serializable {

	private static final long serialVersionUID = 2723711740460904592L;

	private Long id;
	private Set<ParkingLot> parkingLots;
	private Set<Gate> gates;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "section_id", nullable = false)
	public Set<ParkingLot> getSections() {
		return parkingLots;
	}

	public void setSections(Set<ParkingLot> sections) {
		this.parkingLots = sections;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "gate_id", nullable = false)
	public Set<Gate> getGates() {
		return gates;
	}

	public void setGates(Set<Gate> gates) {
		this.gates = gates;
	}

	
}
