package com.parkinglotsystem.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinglotsystem.enums.VehicleType;

@Entity
@Table(name = "vehicle")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 5690908711852625122L;

	private Long id;
	private String number;
	private VehicleType type;
	private VehicleInfo info;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "type")
	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinColumn(name = "vehicleinfo_id", nullable = false)
	public VehicleInfo getInfo() {
		return info;
	}

	public void setInfo(VehicleInfo info) {
		this.info = info;
	}
}
