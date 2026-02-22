package edu.icet.model.dto;

import edu.icet.model.enums.OrderStatus;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "OrderDto", description = "Order data transfer object")
public class OrdersDto {
//    @Schema(description = "Order identifier", example = "1")
    private Long id;

//    @Schema(description = "User ID", example = "1")
    private Long userId;

//    @Schema(description = "Username", example = "john_doe")
    private String username;

//    @Schema(description = "Order status")
    private OrderStatus status;

//    @Schema(description = "Total order amount", example = "199.99")
    private BigDecimal totalAmount;

//    @Schema(description = "Order date")
    private LocalDateTime orderDate;

//    @Schema(description = "Order items")
    private List<OrderItemDto> items;
}
