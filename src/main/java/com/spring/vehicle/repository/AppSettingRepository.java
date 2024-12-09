package com.spring.vehicle.repository;

import com.spring.vehicle.model.entity.AppSetting;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author : Tom
 * {@code @date} : Sun Dec 08 12:26:00 PST 2024
 * @since : 1.0.0
 */

@Repository
public interface AppSettingRepository extends JpaRepository<AppSetting, Long> {
    /**
     * Select id,code,configValue,name,memo,enabled,createdBy,createdDate,lastModifiedBy,lastModifiedDate from app_setting
     */

    public List<AppSetting> findByParentCode(String parentCode);

    Optional<AppSetting> findTopByParentCodeAndCode(String parentCode, String code);
}
