package edu.icet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductRequestDto", description = "Product data for create/update requests")
public class ProductRequestDto {
    @NotBlank(message = "name must not be blank")
    @Schema(description = "Product name", example = "Plain T-Shirt")
    private String name;

    @Size(max = 1000, message = "description must be at most 1000 characters")
    @Schema(description = "Product description", example = "100% cotton t-shirt")
    private String description;

    @Schema(description = "Category reference for the product (provide id)", implementation = CategoryRefDto.class)
    private CategoryRefDto category;
}
