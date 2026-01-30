package edu.icet.controller;

import edu.icet.mapper.ProductMapper;
import edu.icet.model.dto.ProductDto;
import edu.icet.model.dto.ProductRequestDto;
import edu.icet.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "List of products",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)))
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Operation(summary = "Get product by id")
    @ApiResponse(responseCode = "200", description = "Product found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Create a product")
    @ApiResponse(responseCode = "201", description = "Product created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductDto toCreate = productMapper.toDto(requestDto);
        ProductDto created = productService.createProduct(toCreate);
        URI location = URI.create(String.format("/products/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Update a product")
    @ApiResponse(responseCode = "200", description = "Product updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto requestDto) {
        ProductDto toUpdate = productMapper.toDto(requestDto);
        ProductDto updated = productService.updateProduct(id, toUpdate);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a product")
    @ApiResponse(responseCode = "204", description = "Product deleted")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}