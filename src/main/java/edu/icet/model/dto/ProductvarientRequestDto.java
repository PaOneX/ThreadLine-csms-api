package edu.icet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ProductVariantRequestDto", description = "Product variant create/update request")
public class ProductvarientRequestDto {
    @NotNull(message = "productId must not be null")
    @Schema(description = "Product ID this variant belongs to", example = "1", required = true)
    private Long productId;

    @Schema(description = "Variant size", example = "M")
    private String size;

    @Schema(description = "Variant color", example = "Blue")
    private String color;

    @NotNull(message = "price must not be null")
    @Positive(message = "price must be positive")
    @Schema(description = "Variant price", example = "29.99", required = true)
    private BigDecimal price;

    @Schema(description = "SKU code", example = "TSH-BLU-M")
    private String sku;
}
