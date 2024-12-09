package com.spring.vehicle.model.entity;

import com.spring.vehicle.model.entity.audit.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;


/**
 * @author : Tom
 * @date : Sun Dec 08 12:26:00 PST 2024
 * @since : 1.0.0
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_setting")
public class AppSetting extends BaseEntity {

    /**
     * can be used in sequence
     */
    public static final String TABLE = "app_setting";

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -1472460008759850313L;


    /**
     * setting code, unique key
     */
    private String code;


    /**
     * configuration value
     */
    private String configValue;

    /**
     * parentCode
     */
    private String parentCode;


    /**
     * setting name
     */
    private String name;


    /**
     * extra value
     */
    private String memo;


    /**
     * status: enabled 1, disabled 0
     */
    private String enabled;


}
