package com.spring.vehicle.mapper;

import com.spring.vehicle.constant.Constants;
import com.spring.vehicle.model.entity.AppSetting;
import com.spring.vehicle.payload.setting.AppSettingResponse;
import com.spring.vehicle.payload.setting.CreateAppSettingRequest;
import com.spring.vehicle.payload.setting.SettingResponse;
import com.spring.vehicle.payload.setting.UpdateAppSettingRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AppSettingMapper is responsible for converting between AppSetting entities and DTOs (CreateAppSettingRequest, UpdateAppSettingRequest, AppSettingResponse).
 * This keeps the mapping logic separated from the service layer, adhering to the Single Responsibility Principle.
 */
@Component
public class AppSettingMapper {

    /**
     * Build AppSetting entity from the provided request and appSettingId.
     *
     * @param request      The request containing appSetting details.
     * @param appSettingId The ID for the new appSetting.
     * @return The created AppSetting entity.
     */
    public AppSetting buildAppSettingFromRequest(CreateAppSettingRequest request, Long appSettingId) {
        AppSetting appSetting = new AppSetting();
        appSetting.setId(appSettingId);

        appSetting.setCode(request.getCode());
        appSetting.setConfigValue(request.getConfigValue());
        appSetting.setParentCode(request.getParentCode());
        appSetting.setName(request.getName());
        appSetting.setMemo(request.getMemo());
        appSetting.setEnabled(request.getEnabled());

        return appSetting;
    }

    /**
     * Converts a AppSetting entity to a AppSettingResponse DTO.
     *
     * @param appSetting The AppSetting entity to be converted.
     * @return The mapped AppSettingResponse DTO.
     */
    public AppSettingResponse mapToAppSettingResponse(AppSetting appSetting) {
        return AppSettingResponse.builder()
                .id(appSetting.getId())
                .code(appSetting.getCode())
                .configValue(appSetting.getConfigValue())
                .name(appSetting.getName())
                .memo(appSetting.getMemo())
                .enabled(appSetting.getEnabled())
                .build();
    }

    /**
     * Updates an existing AppSetting entity with details from the UpdateAppSettingRequest.
     *
     * @param updateAppSettingRequest The updateAppSettingRequest containing the updated AppSetting details.
     * @param appSetting              The existing AppSetting entity to be updated.
     */
    public void updateAppSettingFromRequest(UpdateAppSettingRequest updateAppSettingRequest, AppSetting appSetting) {
        appSetting.setConfigValue(updateAppSettingRequest.getConfigValue());
    }


    /**
     * Constructs a SettingResponse object based on a list of AppSettingResponse objects.
     *
     * @param codes the list of AppSettingResponse objects
     * @return a SettingResponse object populated with relevant configuration values, or null if the list is null/empty
     */
    public SettingResponse getSettingResponse(List<AppSettingResponse> codes) {
        if (codes == null || codes.isEmpty()) {
            return null;
        }

        SettingResponse settingResponse = new SettingResponse();

        // Populate SettingResponse by iterating through the codes
        codes.forEach(s -> {
            switch (s.getCode()) {
                case Constants.SETTING_RPM -> settingResponse.setRpm(s.getConfigValue());
                case Constants.SETTING_POWER -> settingResponse.setPower(s.getConfigValue());
                case Constants.SETTING_BATTERY -> settingResponse.setBattery(s.getConfigValue());
                case Constants.SETTING_TEMPERATURE -> settingResponse.setTemperature(s.getConfigValue());
                case Constants.SETTING_GEAR_RATIO -> settingResponse.setGearRatio(s.getConfigValue());
                case Constants.SETTING_IS_CHARGING -> settingResponse.setIsCharging(s.getConfigValue()
                                                                                     .equals("1"));

                default -> {
                    // Optionally handle unexpected codes here
                }
            }
        });

        return settingResponse;
    }

}

