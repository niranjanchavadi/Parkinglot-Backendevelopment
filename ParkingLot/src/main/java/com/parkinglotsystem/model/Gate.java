package com.parkinglotsystem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "gate")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Gate implements Serializable {

	private static final long serialVersionUID = -5177827408867142394L;

	private Long id;
	private Short number;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "number")
	public Short getNumber() {
		return number;
	}

	public void setNumber(Short number) {
		this.number = number;
	}



}
