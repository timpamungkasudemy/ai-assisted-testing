package com.course.ai.assistant.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consumers")
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "consumer_uuid", columnDefinition = "UUID")
	private UUID consumerUuid;

	@Column(name = "full_name", nullable = false, length = 255)
	private String fullName;

	@Column(name = "email", nullable = false, unique = true, length = 255)
	private String email;

	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;

	@Column(name = "phone_number", nullable = false, unique = true, length = 255)
	private String phoneNumber;

	@Column(name = "member_number", nullable = false, unique = true, length = 50)
	private String memberNumber;

	@Column(name = "created_by", nullable = false, columnDefinition = "UUID")
	private UUID createdBy;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime createdAt;

	@Column(name = "updated_by", nullable = false, columnDefinition = "UUID")
	private UUID updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime updatedAt;
}
