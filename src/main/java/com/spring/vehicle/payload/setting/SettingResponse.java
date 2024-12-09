package com.spring.vehicle.payload.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@Schema(name = "appSetting  Request", description = "The appSetting  request payload")
public class SettingResponse {

	
	  
	/**
	 * rpm of motor
	 */
	private String rpm;
	
	  
	/**
	 * power consumption
	 */
	private String power;
	
	  
	/**
	 * battery percentage
	 */
	private String battery;

	/**
	 * charging indicator
	 */
	private Boolean isCharging;

	/**
	 * temperature
	 */
	private String temperature;

	/**
	 * Gear ratio
	 */
	private String gearRatio;
}
