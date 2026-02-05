package edu.icet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OrderItemRequestDto", description = "Order item create/update request")
public class OrderItemRequestDto {
    @NotNull(message = "variantId must not be null")
    @Schema(description = "Product variant ID", example = "1", required = true)
    private Long variantId;

    @NotNull(message = "quantity must not be null")
    @Min(value = 1, message = "quantity must be at least 1")
    @Schema(description = "Quantity to order", example = "2", required = true)
    private Integer quantity;

    @Positive(message = "unitPrice must be positive")
    @Schema(description = "Unit price", example = "29.99")
    private BigDecimal unitPrice;
}
