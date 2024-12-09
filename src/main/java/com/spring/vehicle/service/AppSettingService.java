package com.spring.vehicle.service;

import com.spring.vehicle.exception.ResourceNotFoundException;
import com.spring.vehicle.mapper.AppSettingMapper;
import com.spring.vehicle.model.entity.AppSetting;
import com.spring.vehicle.payload.setting.AppSettingResponse;
import com.spring.vehicle.payload.setting.CreateAppSettingRequest;
import com.spring.vehicle.payload.setting.SettingResponse;
import com.spring.vehicle.payload.setting.UpdateAppSettingRequest;
import com.spring.vehicle.repository.AppSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * AppSetting Service.
 *
 * @author : Tom
 * {@code @date} : Sun Dec 08 12:26:00 PST 2024
 * @since : 1.0.0
 */

@Service
public class AppSettingService extends BasicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppSettingService.class);

    private final AppSettingRepository appSettingRepository;
    private final AppSettingMapper appSettingMapper;  // Separate mapper class for mapping entities

    /**
     * Constructor to inject dependencies.
     *
     * @param appSettingRepository AppSettingRepository for database interactions
     * @param appSettingMapper     Mapper for converting entities to DTOs
     */
    public AppSettingService(AppSettingRepository appSettingRepository, AppSettingMapper appSettingMapper) {
        super();
        this.appSettingRepository = requireNonNull(appSettingRepository);
        this.appSettingMapper = requireNonNull(appSettingMapper);
    }

    /**
     * Create a new appSetting and save it to the database.
     * The method is transactional, meaning it ensures that the creation is either fully completed or rolled back.
     *
     * @param createAppSettingRequest The request containing appSetting details.
     * @return Optional containing the created AppSettingResponse.
     */
    public Optional<AppSettingResponse> createAppSetting(CreateAppSettingRequest createAppSettingRequest) {
        // Generate a new appSetting ID
        Long appSettingId = null;// super.nextId(AppSetting.TABLE);
        LOGGER.info("Creating AppSetting: {}", createAppSettingRequest);

        // Convert the request to a AppSetting entity
        AppSetting appSetting = appSettingMapper.buildAppSettingFromRequest(createAppSettingRequest, appSettingId);

        // Save the created appSetting entity to the database
        appSettingRepository.save(appSetting);

        // Return the mapped AppSettingResponse as an Optional
        return Optional.of(appSettingMapper.mapToAppSettingResponse(appSetting));
    }

    /**
     * Delete a appSetting by its ID.
     * The method is transactional and ensures that the deletion is either completed or rolled back.
     * If the appSetting is not found, a ResourceNotFoundException is thrown.
     *
     * @param id The ID of the appSetting to be deleted.
     * @throws ResourceNotFoundException if the appSetting is not found
     */
    public void deleteById(Long id) {
        LOGGER.info("Deleting AppSetting by ID: {}", id);

        // Check if the appSetting exists before attempting deletion
        if (!appSettingRepository.existsById(id)) {
            throw new ResourceNotFoundException("AppSetting", "Id", id);
        }

        // Delete the appSetting by its ID
        appSettingRepository.deleteById(id);
    }

    /**
     * Retrieve a appSetting by its ID.
     * If the appSetting is not found, a ResourceNotFoundException is thrown.
     *
     * @param id The ID of the appSetting to retrieve.
     * @return Optional containing the AppSettingResponse for the given ID.
     * @throws ResourceNotFoundException if the appSetting with the given ID is not found.
     */
    public Optional<AppSettingResponse> findById(Long id) {
        LOGGER.info("Finding AppSetting by Id: {}", id);

        // Find the appSetting by ID and map it to a AppSettingResponse, or throw an exception if not found
        return Optional.ofNullable(appSettingRepository.findById(id)
                                                       .map(appSettingMapper::mapToAppSettingResponse)
                                                       .orElseThrow(() -> new ResourceNotFoundException("AppSetting", "Id", id)));
    }

    /**
     * Update an existing appSetting by its ID.
     * The method is transactional, ensuring that the update is either completed or rolled back.
     * If the appSetting is not found, a ResourceNotFoundException is thrown.
     *
     * @param updateAppSettingRequest The request containing updated appSetting details.
     * @return Optional containing the updated AppSettingResponse.
     * @throws ResourceNotFoundException if the appSetting with the given ID is not found.
     */
    public Optional<AppSettingResponse> updateAppSetting(UpdateAppSettingRequest updateAppSettingRequest) {
        LOGGER.info("Updating AppSetting with ID: {}", updateAppSettingRequest);

        // Find the appSetting by ID, update its fields, save it, and map it to a response
        return appSettingRepository.findTopByParentCodeAndCode(updateAppSettingRequest.getParentCode(),updateAppSettingRequest.getCode())
                                   .map(appSetting -> {
                                       // Update appSetting entity fields using the provided request data
                                       appSettingMapper.updateAppSettingFromRequest(updateAppSettingRequest, appSetting);
                                       appSettingRepository.save(appSetting);  // Save the updated appSetting entity
                                       return Optional.of(appSettingMapper.mapToAppSettingResponse(appSetting));
                                   })
                                   .orElseThrow(() -> new ResourceNotFoundException("AppSetting", "Id", updateAppSettingRequest.getParentCode() +"-"+updateAppSettingRequest.getCode()));
    }

    /**
     * Finds a list of AppSettingResponse objects based on the provided parent code.
     * Maps the AppSetting entities to AppSettingResponse objects.
     *
     * @param parentCode the code of the parent setting used to filter the settings
     * @return an Optional containing the list of AppSettingResponse objects, or an empty Optional if no settings are found
     */
    public Optional<List<AppSettingResponse>> findByParentCode(String parentCode) {
        // Fetch the list of AppSetting entities from the repository based on the parent code
        List<AppSetting> list = appSettingRepository.findByParentCode(parentCode);

        // Return an empty Optional if the list is null or empty
        if (list == null || list.isEmpty()) {
            return Optional.empty();
        }

        // Use Stream API to map entities to DTOs and collect them into a list
        List<AppSettingResponse> responseList = list.stream()
                                                    .map(appSettingMapper::mapToAppSettingResponse)
                                                    .toList();

        // Wrap the mapped list in an Optional and return
        return Optional.of(responseList);
    }

    /**
     * Retrieves the SettingResponse object based on the given setting code.
     *
     * @param setting the parent code used to fetch the settings
     * @return an Optional containing the SettingResponse, or Optional.empty() if no settings are found
     */
    public Optional<SettingResponse> getSetting(String setting) {
        // Fetch the list of AppSettingResponse objects and map them to SettingResponse
        return findByParentCode(setting).map(appSettingMapper::getSettingResponse);
    }
}
