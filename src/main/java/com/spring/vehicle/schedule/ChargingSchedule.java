package com.spring.vehicle.schedule;

import com.spring.vehicle.constant.Constants;
import com.spring.vehicle.exception.ResourceNotFoundException;
import com.spring.vehicle.payload.setting.SettingResponse;
import com.spring.vehicle.payload.setting.UpdateAppSettingRequest;
import com.spring.vehicle.service.AppSettingService;
import com.spring.vehicle.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ChargingSchedule {

    private static final Logger log = LoggerFactory.getLogger(ChargingSchedule.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final int MAX_BATTERY = 100;
    private static final int MIN_BATTERY = 0;
    private static final int MAX_POWER = 1000;
    private static final int DEFAULT_POWER = 100;
    private static final int MAX_TEMPERATURE = 120;
    private static final int DEFAULT_TEMPERATURE = 20;

    private final AppSettingService appSettingService;

    public ChargingSchedule(AppSettingService appSettingService) {
        this.appSettingService = appSettingService;
    }

    /**
     * Scheduled task to reset application settings periodically.
     * This method executes every 5 seconds (5000ms).
     */
    @Scheduled(fixedRate = 5000)
    public void resetAppSetting() {
        log.info("The time is now {}", dateFormat.format(new Date()));

        // Fetch the current settings; throw exception if not found
        SettingResponse settingResponse = appSettingService.getSetting(Constants.SETTING)
                                                           .orElseThrow(() -> new ResourceNotFoundException("Code", "Id", Constants.SETTING));
        log.info("Current setting response: {}", settingResponse);

        // Parse numeric values from the settings
        int rpm = MathUtils.parseInt(settingResponse.getRpm());
        int power = MathUtils.parseInt(settingResponse.getPower());
        int battery = MathUtils.parseInt(settingResponse.getBattery());
        int temperature = MathUtils.parseInt(settingResponse.getTemperature());

        // Prepare a reusable request object for updates
        UpdateAppSettingRequest updateRequest = new UpdateAppSettingRequest();
        updateRequest.setParentCode(Constants.SETTING);

        // Handle charging logic
        if (Boolean.TRUE.equals(settingResponse.getIsCharging())) {
            log.info("Device is charging. Increasing battery level from {}", battery);
            battery = Math.min(battery + 1, MAX_BATTERY); // Ensure battery does not exceed max
            updateRequest.setCode(Constants.SETTING_BATTERY);
            updateRequest.setConfigValue(String.valueOf(battery));
            appSettingService.updateAppSetting(updateRequest);

            // Adjust power based on battery
            power = battery / 100 * 1000;
            updateRequest.setCode(Constants.SETTING_POWER);
            updateRequest.setConfigValue(String.valueOf(power));
            appSettingService.updateAppSetting(updateRequest);

            // Increase temperature with a max limit and battery not fully charged
            if (battery < 100) {
                temperature = Math.min(temperature + 1, MAX_TEMPERATURE);
                updateRequest.setCode(Constants.SETTING_TEMPERATURE);
                updateRequest.setConfigValue(String.valueOf(temperature));
                appSettingService.updateAppSetting(updateRequest);
            }
        }

        // Handle running logic
        if (rpm > 0) {
            log.info("Device is running. RPM: {}, Decreasing battery, and adjusting power/temperature.", rpm);

            // Decrease battery with a minimum limit
            battery = Math.max(battery - 1, MIN_BATTERY);
            updateRequest.setCode(Constants.SETTING_BATTERY);
            updateRequest.setConfigValue(String.valueOf(battery));
            appSettingService.updateAppSetting(updateRequest);

            //stop running while battery fall to 0
            if (battery == 0) {
                rpm = 0;
                updateRequest.setCode(Constants.SETTING_RPM);
                updateRequest.setConfigValue("0");
                appSettingService.updateAppSetting(updateRequest);
            }

            // Adjust power based on RPM
            power = rpm / 800 * 1000;
            updateRequest.setCode(Constants.SETTING_POWER);
            updateRequest.setConfigValue(String.valueOf(power));
            appSettingService.updateAppSetting(updateRequest);

            // Increase temperature with a max limit
            temperature = Math.min(temperature + 2, MAX_TEMPERATURE);
            updateRequest.setCode(Constants.SETTING_TEMPERATURE);
            updateRequest.setConfigValue(String.valueOf(temperature));
            appSettingService.updateAppSetting(updateRequest);
        }

        //motor stop and not charging
        if (Boolean.FALSE.equals(settingResponse.getIsCharging()) && rpm == 0) {
            // Reset temperature to default when not running
            log.info("Device is idle. Resetting temperature to default: {}", DEFAULT_TEMPERATURE);
            updateRequest.setCode(Constants.SETTING_TEMPERATURE);
            updateRequest.setConfigValue(String.valueOf(DEFAULT_TEMPERATURE));
            appSettingService.updateAppSetting(updateRequest);
        }
    }
}
