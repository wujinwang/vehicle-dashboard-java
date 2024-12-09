package com.spring.vehicle.payload.setting;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "appSetting  Request", description = "The appSetting  request payload")
public class UpdateAppSettingRequest {


    /**
     * setting code, unique key
     */
    @NotNull(message = "appSetting code cannot be blank")
    @Schema(name = "Valid appSetting code", allowableValues = "String")
    private String code;


    /**
     * configuration value
     */
    @NotNull(message = "appSetting code cannot be blank")
    @Schema(name = "Valid appSetting code", allowableValues = "String")
    private String configValue;

    /**
     * parentCode
     */
    @Schema(name = "parentCode", allowableValues = "String")
    private String parentCode;

}
