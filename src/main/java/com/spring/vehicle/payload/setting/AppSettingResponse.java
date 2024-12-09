package com.spring.vehicle.payload.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "appSetting  Request", description = "The appSetting  request payload")
public class AppSettingResponse {


    /**
     * primary key
     */
    @Schema(name = "Valid appSetting", allowableValues = "Long")
    private Long id;


    /**
     * setting code, unique key
     */
    @Schema(name = "Valid appSetting", allowableValues = "String")
    private String code;


    /**
     * configuration value
     */
    @Schema(name = "Valid appSetting", allowableValues = "String")
    private String configValue;

    /**
     * parentCode
     */
    @Schema(name = "parentCode", allowableValues = "String")
    private String parentCode;

    /**
     * setting name
     */
    @Schema(name = "Valid appSetting", allowableValues = "String")
    private String name;


    /**
     * extra value
     */
    @Schema(name = "Valid appSetting", allowableValues = "String")
    private String memo;


    /**
     * status: enabled 1, disabled 0
     */
    @Schema(name = "Valid appSetting", allowableValues = "String")
    private String enabled;


}
