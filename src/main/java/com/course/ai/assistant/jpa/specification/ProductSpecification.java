package com.course.ai.assistant.jpa.specification;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.course.ai.assistant.constant.JpaConstants.ActiveQueryFlag;
import com.course.ai.assistant.constant.JpaConstants.SortDirection;
import com.course.ai.assistant.entity.ProductEntity;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {

  public static Specification<ProductEntity> productNameLikeIgnoreCase(String productName) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.upper(root.get("productName")),
        productName.toUpperCase());
  }

  public static Specification<ProductEntity> priceBetween(Double minPrice, Double maxPrice) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
  }

  public static Specification<ProductEntity> productStockKeepingUnitEqualsIgnoreCase(String stockKeepingUnit) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
        criteriaBuilder.upper(root.get("stockKeepingUnit")),
        stockKeepingUnit.toUpperCase());
  }

  public static Specification<ProductEntity> search(String name, String sku, Double minPrice, Double maxPrice,
      ActiveQueryFlag activeQueryFlag, String sortColumn, SortDirection sortDirection) {
    return (root, query, criteriaBuilder) -> {
      Predicate predicate = criteriaBuilder.conjunction();

      if (StringUtils.isNotBlank(name)) {
        predicate = criteriaBuilder.and(predicate,
            productNameLikeIgnoreCase(name).toPredicate(root, query, criteriaBuilder));
      }

      if (StringUtils.isNotBlank(sku)) {
        predicate = criteriaBuilder.and(predicate,
            productStockKeepingUnitEqualsIgnoreCase(sku).toPredicate(root, query, criteriaBuilder));
      }

      if (Objects.nonNull(minPrice) && Objects.nonNull(maxPrice) && minPrice <= maxPrice) {
        predicate = criteriaBuilder.and(predicate,
            priceBetween(minPrice, maxPrice).toPredicate(root, query, criteriaBuilder));
      }

      if (Optional.ofNullable(activeQueryFlag).orElse(ActiveQueryFlag.ALL) != ActiveQueryFlag.ALL) {
        var activeQueryFlagValue = (activeQueryFlag == ActiveQueryFlag.ACTIVE_ONLY) ? true : false;

        predicate = criteriaBuilder.and(predicate,
            criteriaBuilder.equal(root.get("active"), activeQueryFlagValue));
      }

      if (StringUtils.isNotBlank(sortColumn)) {
        if (Objects.nonNull(sortDirection) && sortDirection == SortDirection.DESC) {
          query.orderBy(criteriaBuilder.desc(root.get(sortColumn)), criteriaBuilder.asc(root.get("productName")));
        } else {
          query.orderBy(criteriaBuilder.asc(root.get(sortColumn)), criteriaBuilder.asc(root.get("productName")));
        }
      } else {
        query.orderBy(criteriaBuilder.desc(root.get("updatedAt")), criteriaBuilder.asc(root.get("productName")));
      }

      return predicate;
    };
  }

}
