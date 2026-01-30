package edu.icet.controller;

import edu.icet.mapper.CategoryMapper;
import edu.icet.model.dto.CategoryDto;
import edu.icet.model.dto.CategoryRequestDto;
import edu.icet.service.CategoryService;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Get all categories")
    @ApiResponse(responseCode = "200", description = "List of categories",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class)))
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @Operation(summary = "Get category by id")
    @ApiResponse(responseCode = "200", description = "Category found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class)))
    @ApiResponse(responseCode = "404", description = "Category not found")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Create a category")
    @ApiResponse(responseCode = "201", description = "Category created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryDto toCreate = categoryMapper.toDto(categoryRequestDto);
        CategoryDto created = categoryService.createCategory(toCreate);
        URI location = URI.create(String.format("/categories/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Update a category")
    @ApiResponse(responseCode = "200", description = "Category updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class)))
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryDto toUpdate = categoryMapper.toDto(categoryRequestDto);
        CategoryDto updated = categoryService.updateCategory(id, toUpdate);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a category")
    @ApiResponse(responseCode = "204", description = "Category deleted")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}