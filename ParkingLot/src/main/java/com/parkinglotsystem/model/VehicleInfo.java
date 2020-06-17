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
@Table(name = "vehicleinfo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VehicleInfo implements Serializable {

	private static final long serialVersionUID = 4866416163867005563L;

	private Long id;
	private Date inTime;
	private Date outTime;
	private Short amount;
	private Gate entryGate;
	private Gate exitGate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Column(name = "outtime")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	@Column(name = "amount")
	public Short getAmount() {
		return amount;
	}

	public void setAmount(Short amount) {
		this.amount = amount;
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

	
}
