package com.spring.vehicle.model.entity.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

/**
 * Created by Tom
 */

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdBy", "lastModifiedBy" }, allowGetters = true)
public abstract class BaseEntity extends DateAudit {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -6987582165177290567L;

	/**
	 * primary key
	 */
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@CreatedBy
	public String createdBy;

	@LastModifiedBy
	public String lastModifiedBy;
}
