package com.course.ai.assistant.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.course.ai.assistant.api.request.ProductRequest;
import com.course.ai.assistant.api.response.ProductResponse;
import com.course.ai.assistant.constant.JpaConstants;
import com.course.ai.assistant.constant.JpaConstants.ActiveQueryFlag;
import com.course.ai.assistant.constant.JpaConstants.SortDirection;
import com.course.ai.assistant.entity.ProductEntity;
import com.course.ai.assistant.jpa.repository.ProductRepository;
import com.course.ai.assistant.jpa.specification.ProductSpecification;
import com.course.ai.assistant.mapper.ProductMapper;

@Service
public class ProductService {

  private ProductMapper productMapper;

  private ProductRepository productRepository;

  public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
    this.productMapper = productMapper;
    this.productRepository = productRepository;
  }

  public void createProduct(ProductRequest productRequest) {
    var productEntity = productMapper.requestToEntity(productRequest);
    productRepository.save(productEntity);
  }

  public ProductResponse findByProductUuid(UUID productUuid) {
    var productEntity = productRepository.findById(productUuid).orElseThrow();
    return productMapper.entityToResponse(productEntity);
  }

  public List<ProductEntity> findByProductNameLike(String productName) {
    return productRepository.findAll(ProductSpecification.productNameLikeIgnoreCase(productName));
  }

  public List<ProductEntity> findByPriceBetween(Double minPrice, Double maxPrice) {
    return productRepository.findAll(ProductSpecification.priceBetween(minPrice, maxPrice));
  }

  public ProductEntity findByStockKeepingUnit(String stockKeepingUnit) {
    return productRepository.findOne(ProductSpecification.productStockKeepingUnitEqualsIgnoreCase(stockKeepingUnit))
        .orElseThrow();
  }

  public List<ProductEntity> search(String name, Double minPrice, Double maxPrice, String sku,
      ActiveQueryFlag activeQueryFlag, String sortColumn,
      SortDirection sortDirection) {
    return productRepository
        .findAll(ProductSpecification.search(name, sku, minPrice, maxPrice, activeQueryFlag, sortColumn,
            sortDirection));
  }

  public ProductEntity updateProduct(UUID productUuid, ProductRequest updatedProductRequest) {
    var queryResult = productRepository.findById(productUuid);

    if (queryResult.isEmpty()) {
      throw new NoSuchElementException(String.format("Product UUID %v not found", productUuid));
    }

    var existingProductEntity = queryResult.get();
    existingProductEntity.setBasePrice(updatedProductRequest.getBasePrice());
    existingProductEntity.setDescription(updatedProductRequest.getDescription());
    existingProductEntity.setManufacturer(updatedProductRequest.getManufacturer());
    existingProductEntity.setName(updatedProductRequest.getName());
    existingProductEntity.setStockKeepingUnit(updatedProductRequest.getStockKeepingUnit());
    existingProductEntity.setUpdatedBy(JpaConstants.DEFAULT_UPDATED_BY);
    existingProductEntity.setUpdatedAt(ZonedDateTime.now());

    return productRepository.save(existingProductEntity);
  }

}
