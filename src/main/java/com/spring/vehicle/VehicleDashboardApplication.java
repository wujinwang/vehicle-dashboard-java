package com.spring.vehicle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class VehicleDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleDashboardApplication.class, args);
	}

}
