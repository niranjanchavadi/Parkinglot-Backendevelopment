package com.parkinglotsystem.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "vehicleentry")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VehicleEntry implements Serializable {

	private static final long serialVersionUID = 816436113837233081L;

	private long id;
	private Date inTime;
	private Date outTime;
	private Vehicle vehicle;
	private Gate entryGate;
	private Gate exitGate;
	private Slot slot;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "intime", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	@Column(name = "outtime", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", nullable = false)
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gateentry_id", nullable = false)
	public Gate getEntryGate() {
		return entryGate;
	}

	public void setEntryGate(Gate entryGate) {
		this.entryGate = entryGate;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gateexit_id")
	public Gate getExitGate() {
		return exitGate;
	}

	public void setExitGate(Gate exitGate) {
		this.exitGate = exitGate;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "slot_id", nullable = false)
	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

}
