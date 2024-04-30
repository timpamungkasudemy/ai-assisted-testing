package com.course.ai.assistant.api.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.response.SimpleMessageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.datafaker.Faker;

@RestController
@Tag(name = "Promotion")
public class PromotionController {

  private Faker faker = new Faker();

  @Operation(summary = "Redirect to promotion page")
  @GetMapping(path = "/api/promotion/fake/{promotion-code}")
  public ResponseEntity<Void> redirectToPromotionPage(@PathVariable(name = "promotion-code") String promotionCode) {
    return ResponseEntity.status(HttpStatus.FOUND)
        .location(URI.create("/alphamart/html/promotion/detail/" + promotionCode)).build();
  }

  @Operation(summary = "Get promotion details", hidden = true)
  @GetMapping(path = "/html/promotion/detail/{promotion-code}", produces = MediaType.TEXT_HTML_VALUE)
  public ResponseEntity<String> getPromotionDetails(@PathVariable(name = "promotion-code") String promotionCode) {
    // show the promotionCode in the page
    return ResponseEntity.ok("<html><head><title>" + promotionCode
        + "</title></head><body><h2>Promotion details for: <strong>" + promotionCode + "</strong></h2></body></html>");
  }

  @Operation(summary = "Generate article")
  @GetMapping(path = "/api/promotion/article", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SimpleMessageResponse> generateArticle(
      @RequestParam(name = "sentence-count", required = true) @Parameter(description = "Article size (in sentences)") int sentenceCount) {
    var article = faker.lorem().paragraph(sentenceCount);

    return ResponseEntity.ok(SimpleMessageResponse.builder().message(article).build());
  }

}
