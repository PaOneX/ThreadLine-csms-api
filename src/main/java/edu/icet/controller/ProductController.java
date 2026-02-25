package edu.icet.controller;

import edu.icet.mapper.ProductMapper;
import edu.icet.model.dto.ProductDto;
import edu.icet.model.dto.ProductRequestDto;
import edu.icet.service.ProductService;
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

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        ProductDto toCreate = productMapper.toDto(requestDto);
        ProductDto created = productService.createProduct(toCreate);
        URI location = URI.create(String.format("/products/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) {
        ProductDto toUpdate = productMapper.toDto(requestDto);
        ProductDto updated = productService.updateProduct(id, toUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}