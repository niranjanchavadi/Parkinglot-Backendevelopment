package com.parkinglotsystem;

import org.springframework.boot.SpringApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ParkingSystemApplication {

	 private static final Logger LOG = LogManager.getLogger(ParkingSystemApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ParkingSystemApplication.class, args);
		  LOG.debug("This will be printed on debug");
	        LOG.info("This will be printed on info");
	        LOG.warn("This will be printed on warn");
	        LOG.info("Appending string: {}.", "Hello");
	}
}
