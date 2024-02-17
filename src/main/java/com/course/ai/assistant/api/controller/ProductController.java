package com.course.ai.assistant.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.request.ProductRequest;
import com.course.ai.assistant.api.response.PaginationResponse;
import com.course.ai.assistant.api.response.ProductCreateResponse;
import com.course.ai.assistant.api.response.ProductResponse;
import com.course.ai.assistant.api.response.ProductSearchResponse;
import com.course.ai.assistant.constant.JpaConstants;
import com.course.ai.assistant.constant.JpaConstants.ActiveQueryFlag;
import com.course.ai.assistant.entity.ProductEntity;
import com.course.ai.assistant.mapper.ProductMapper;
import com.course.ai.assistant.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Product")
public class ProductController {

  private ProductService productService;

  private ProductMapper productMapper;

  public ProductController(ProductService productService, ProductMapper productMapper) {
    this.productService = productService;
    this.productMapper = productMapper;
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Create a new product")
  @ApiResponses({ @ApiResponse(responseCode = "201", description = "Product created") })
  public ResponseEntity<ProductCreateResponse> createProduct(
      @RequestBody(required = true) ProductRequest newProduct) {
    var newEntity = productService.createProduct(newProduct);
    var responseBody = ProductCreateResponse.builder().productUuid(newEntity.getProductUuid()).build();

    return ResponseEntity.created(null).body(responseBody);
  }

  @GetMapping(path = "/{product-uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Find product by UUID")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "Product found"),
      @ApiResponse(responseCode = "400", description = "Product with given UUID not found") })
  public ProductResponse findByProductUuid(
      @PathVariable(name = "product-uuid", required = true) @Parameter(name = "product-uuid", description = "Product UUID to find", example = "d961c1ff-0580-4f49-9b65-463e9ed63652") UUID productUuid) {
    var entity = productService.findByProductUuid(productUuid);
    return productMapper.entityToResponse(entity);
  }

  @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ProductSearchResponse search(
      @RequestParam(name = "name", required = false) @Parameter(description = "Find by product name like ... (case insensitive)", example = "chocolate") String name,
      @RequestParam(name = "sku", required = false) @Parameter(description = "Find by Stock Keeping Unit (SKU) equals ... (case insensitive)", example = "SKU12318") String sku,
      @RequestParam(name = "min-price", required = false, defaultValue = "0") @Parameter(description = "Minimum price", example = "1") Double minPrice,
      @RequestParam(name = "max-price", required = false, defaultValue = "0") @Parameter(description = "Maximum price", example = "999") Double maxPrice,
      @RequestParam(name = "active-flag", required = false, defaultValue = "ALL") @Parameter(description = """
          Active query flag (case insensitive).
          <ul>
            <li><code>ALL</code> - all products</li>
            <li><code>ACTIVE_ONLY</code> - only active products</li>
            <li><code>INACTIVE_ONLY</code> - only inactive products</li>
          </ul>
          """) ActiveQueryFlag activeQueryFlag,
      @RequestParam(name = "sort-by", required = false, defaultValue = "name") @Parameter(description = """
          Sort column (case sensitive).
          <ul>
            <li><code>name</code> - sort by product name</li>
            <li><code>stockKeepingUnit</code> - sort by SKU</li>
            <li><code>price</code> - sort by price</li>
            <li><code>active</code> - sort by active flag</li>
            <li><code>createdAt</code> - sort by creation date</li>
            <li><code>updatedAt</code> - sort by update date</li>
          </ul>
          """) String sortColumn,
      @RequestParam(name = "sort-direction", required = false, defaultValue = "ASC") @Parameter(description = """
          Sort direction (case insensitive).
          <ul>
            <li><code>ASC</code> - ascending</li>
            <li><code>DESC</code> - descending</li>
          </ul>
          """) JpaConstants.SortDirection sortDirection,
      @RequestParam(name = "page", required = false, defaultValue = "1") @Parameter(description = "Page number (1-based)", example = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "20") @Parameter(description = "Page size", example = "20") Integer size) {
    var pageSearch = productService.search(name, minPrice, maxPrice, sku, activeQueryFlag, sortColumn, sortDirection,
        Pageable.ofSize(size).withPage(page - 1));

    var pagination = PaginationResponse.builder().page(pageSearch.getNumber() + 1).size(pageSearch.getSize())
        .totalElements(pageSearch.getTotalElements()).totalPages(pageSearch.getTotalPages()).build();
    var products = productMapper.entityToResponse(pageSearch.getContent());

    return ProductSearchResponse.builder().data(products).pagination(pagination).build();
  }

  @PutMapping(path = "/{product-uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ProductEntity updateProduct(
      @PathVariable(name = "product-uuid", required = true) @Parameter(name = "product-uuid", description = "Product UUID to update", example = "d961c1ff-0580-4f49-9b65-463e9ed63652") UUID productUuid,
      @RequestBody(required = true) ProductRequest updatedProductRequest) {
    return productService.updateProduct(productUuid, updatedProductRequest);
  }

}
