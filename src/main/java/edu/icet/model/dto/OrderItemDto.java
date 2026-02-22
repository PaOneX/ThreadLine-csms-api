package edu.icet.model.dto;

//import io.swagger.swaggerv3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "OrderItemDto", description = "Order item data transfer object")
public class OrderItemDto {
//    @Schema(description = "Order item identifier", example = "1")
    private Long id;
//    @Schema(description = "Order ID", example = "1")
    private Long orderId;

//    @Schema(description = "Product variant ID", example = "1")
    private Long variantId;

//    @Schema(description = "Product name", example = "Plain T-Shirt")
    private String productName;

//    @Schema(description = "Variant size", example = "M")
    private String variantSize;

//    @Schema(description = "Variant color", example = "Blue")
    private String variantColor;

//    @Schema(description = "Quantity ordered", example = "2")
    private Integer quantity;

//    @Schema(description = "Unit price", example = "29.99")
    private BigDecimal unitPrice;
}
