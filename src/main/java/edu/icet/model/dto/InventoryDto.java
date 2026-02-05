package edu.icet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "InventoryDto", description = "Inventory data transfer object")
public class InventoryDto {
    @Schema(description = "Inventory identifier", example = "1")
    private Long id;

    @Schema(description = "Product variant ID", example = "1")
    private Long variantId;

    @Schema(description = "Product variant SKU", example = "TSH-BLU-M")
    private String variantSku;

    @Schema(description = "Product name", example = "Plain T-Shirt")
    private String productName;

    @Schema(description = "Available quantity", example = "100")
    private Integer quantity;

    @Schema(description = "Last restock timestamp")
    private LocalDateTime lastRestocked;
}
