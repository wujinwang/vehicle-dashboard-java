package com.spring.vehicle.controller;


import com.spring.vehicle.constant.Constants;
import com.spring.vehicle.exception.BadRequestException;
import com.spring.vehicle.exception.ResourceNotFoundException;
import com.spring.vehicle.payload.setting.AppSettingResponse;
import com.spring.vehicle.payload.setting.SettingResponse;
import com.spring.vehicle.payload.setting.UpdateAppSettingRequest;
import com.spring.vehicle.service.AppSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.spring.vehicle.constant.PathConstants.API_V1;

/**
 * AppSetting Rest API controller, defining endpoints for managing appSetting resources.
 *
 * @author : Tom
 * {@code @date} : Sun Dec 08 12:26:00 PST 2024
 * @since : 1.0.0
 */
@Tag(name = "AppSetting Rest API", description = "Defines endpoints that manage appSetting.")
@Controller
@RequestMapping(API_V1)
class SettingController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(SettingController.class);

    private final AppSettingService appSettingService;

    public SettingController(AppSettingService appSettingService) {
        this.appSettingService = appSettingService;
    }

    /**
     * Endpoint to retrieve a AppSetting by its unique ID.
     *
     * @param id the unique identifier of the appSetting
     * @return ResponseEntity containing the AppSettingResponse or an error if not found
     */
    @Operation(summary = "Get appSetting by Id")
    @GetMapping("/setting/{id}")
    public ResponseEntity<AppSettingResponse> getAppSetting(@PathVariable("id") Long id) {
        LOGGER.info("Find AppSetting by Id:{}", id);
        return appSettingService.findById(id)
                                .map(appSettingResponse -> new ResponseEntity<>(appSettingResponse, HttpStatus.OK))
                                .orElseThrow(() -> new ResourceNotFoundException("AppSetting", "Id", id));
    }


    /**
     * Endpoint to update an existing appSetting.
     *
     * @param id                      the unique identifier of the appSetting to update
     * @param updateAppSettingRequest request body with updated AppSetting data
     * @return ResponseEntity containing updated AppSettingResponse or error if update fails
     */
    @Operation(summary = "Update appSetting")
    @PutMapping("/setting")
    public ResponseEntity<AppSettingResponse> updateAppSetting(@RequestBody UpdateAppSettingRequest updateAppSettingRequest) {
        LOGGER.info("Update AppSetting:{}", updateAppSettingRequest);
        return appSettingService.updateAppSetting(updateAppSettingRequest)
                                .map(appSettingResponse -> new ResponseEntity<>(appSettingResponse, HttpStatus.OK))
                                .orElseThrow(() -> new BadRequestException("Update AppSetting Failed."));
    }


    /**
     * Get setting of vehicle dashboard
     *
     * @return ResponseEntity containing the list of codes
     */
    @Operation(summary = "Get setting")
    @GetMapping("/settings")
    public ResponseEntity<SettingResponse> getSettings() {

        // Call service to fetch codes, and throw exception if not found
        SettingResponse settingResponse = appSettingService.getSetting(Constants.SETTING)
                                                           .orElseThrow(() -> new ResourceNotFoundException("Code", "Id", Constants.SETTING));

        return ResponseEntity.ok(settingResponse);
    }


}
