package edu.icet.model.dto;

import edu.icet.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "OrderRequestDto", description = "Order create/update request")
public class OrderRequestDto {
    //    @Schema(description = "User ID placing the order", example = "1")
    private Long userId;

    //    @Schema(description = "Order status")
    private OrderStatus status;

    //    @Schema(description = "Total order amount", example = "199.99")
    private BigDecimal totalAmount;

    //    @NotNull(message = "Order items must not be null")
//    @Schema(description = "Order items")
    private List<OrderItemRequestDto> items;
}
