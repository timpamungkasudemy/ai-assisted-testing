package com.course.ai.assistant.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.course.ai.assistant.constant.JpaConstants;

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

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID productUuid;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "manufacturer")
  private String manufacturer;

  @Column(name = "base_price", nullable = false)
  private double basePrice;

  @Column(name = "description")
  private String description;

  @Column(name = "stock_keeping_unit", nullable = false)
  private String stockKeepingUnit;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private ZonedDateTime updatedAt;

  @Builder.Default
  @Column(name = "created_by", nullable = false)
  private String createdBy = JpaConstants.DEFAULT_CREATED_BY;

  @Builder.Default
  @Column(name = "updated_by", nullable = false)
  private String updatedBy = JpaConstants.DEFAULT_UPDATED_BY;

  @Column(name = "active", nullable = false)
  private boolean active;

}
