package edu.icet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemRequestDto {
    private Long orderId;
    private Long productVariantId;
    private Integer quantity;
    private Double price;
}
