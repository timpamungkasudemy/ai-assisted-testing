package com.course.ai.assistant.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.course.ai.assistant.api.request.ProductRequest;
import com.course.ai.assistant.api.response.ProductResponse;
import com.course.ai.assistant.entity.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductResponse entityToResponse(ProductEntity entity);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  @Mapping(target = "productUuid", ignore = true)
  ProductEntity requestToEntity(ProductRequest request);

  List<ProductResponse> entityToResponse(List<ProductEntity> entityList);

}
