package com.parkinglotsystem.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinglotsystem.enums.ActorType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking")
@Data
@NoArgsConstructor
public class User {
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private long id;

	
	private String firstName;
	
	private String lastName;
	
	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String phoneNo;
	
	
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
	private ActorType actorType;
	
	@JsonIgnore
	@Column(columnDefinition = "boolean default false")
	private boolean isVerified;
	
	@JsonIgnore
	private LocalDateTime createDate;
	
	@JsonIgnore
	private LocalDateTime modifiedDate;
	
	@JsonIgnore
	@Column(columnDefinition = "boolean default false")
	private boolean userStatus;
	
	
	
}

