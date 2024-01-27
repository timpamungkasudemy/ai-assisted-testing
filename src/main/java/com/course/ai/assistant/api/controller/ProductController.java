package com.course.ai.assistant.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.request.ProductRequest;
import com.course.ai.assistant.api.response.ProductResponse;
import com.course.ai.assistant.constant.JpaConstants;
import com.course.ai.assistant.constant.JpaConstants.ActiveQueryFlag;
import com.course.ai.assistant.entity.ProductEntity;
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

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(path = "/{product-uuid}")
  @Operation(summary = "Find product by UUID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Product found"),
      @ApiResponse(responseCode = "400", description = "Product with given UUID not found")
  })
  public ProductResponse findByProductUuid(
      @PathVariable(name = "product-uuid", required = true) @Parameter(name = "product-uuid", description = "Product UUID to find", example = "d961c1ff-0580-4f49-9b65-463e9ed63652") UUID productUuid) {
    return productService.findByProductUuid(productUuid);
  }

  @GetMapping(path = "/search")
  public List<ProductResponse> findByProductNameLike(
      @RequestParam(name = "name", required = false) @Parameter(description = "Product name (case insensitive)") String name,
      @RequestParam(name = "sku", required = false) @Parameter(description = "Stock Keeping Unit (SKU)") String sku,
      @RequestParam(name = "min-price", required = false, defaultValue = "0") @Parameter(description = "Minimum price") Double minPrice,
      @RequestParam(name = "max-price", required = false, defaultValue = "0") @Parameter(description = "Maximum price") Double maxPrice,
      @RequestParam(required = false, defaultValue = "ALL") @Parameter(description = """
          Active query flag (case insensitive).
          <ul>
            <li><code>ALL</code> - all products</li>
            <li><code>ACTIVE_ONLY</code> - only active products</li>
            <li><code>INACTIVE_ONLY</code> - only inactive products</li>
          </ul>
          """) ActiveQueryFlag activeQueryFlag,
      @RequestParam(required = false, defaultValue = "productName") @Parameter(description = """
          Sort column (case sensitive).
          <ul>
            <li><code>productName</code> - sort by product name</li>
            <li><code>stockKeepingUnit</code> - sort by SKU</li>
            <li><code>price</code> - sort by price</li>
            <li><code>active</code> - sort by active flag</li>
            <li><code>createdAt</code> - sort by creation date</li>
            <li><code>updatedAt</code> - sort by update date</li>
          </ul>
          """) String sortColumn,
      @RequestParam(required = false, defaultValue = "ASC") @Parameter(description = """
          Sort direction (case insensitive).
          <ul>
            <li><code>ASC</code> - ascending</li>
            <li><code>DESC</code> - descending</li>
          </ul>
          """) JpaConstants.SortDirection sortDirection) {
    var entities = productService.search(name, minPrice, maxPrice, sku, activeQueryFlag, sortColumn, sortDirection);

    return null;
  }

  @GetMapping(path = "/price")
  public List<ProductEntity> findByPriceBetween(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
    return productService.findByPriceBetween(minPrice, maxPrice);
  }

  @GetMapping(path = "/sku/{sku}")
  public ProductEntity findByStockKeepingUnit(@PathVariable String sku) {
    return productService.findByStockKeepingUnit(sku);
  }

  @PutMapping(path = "/{id}")
  public ProductEntity updateProduct(@PathVariable UUID id, @RequestBody ProductRequest updatedProductRequest) {
    return productService.updateProduct(id, updatedProductRequest);
  }

}
