package edu.icet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ProductVariantDto", description = "Product variant data transfer object")
public class ProductVarientDto {
    @Schema(description = "Variant identifier", example = "1")
    private Long id;

    @Schema(description = "Product ID this variant belongs to", example = "1")
    private Long productId;

    @Schema(description = "Product name", example = "Plain T-Shirt")
    private String productName;

    @Schema(description = "Variant size", example = "M")
    private String size;

    @Schema(description = "Variant color", example = "Blue")
    private String color;

    @Schema(description = "Variant price", example = "29.99")
    private BigDecimal price;

    @Schema(description = "SKU code", example = "TSH-BLU-M")
    private String sku;
}
