package edu.icet.model.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"order", "variant"})
public class OrderItem {
    private Long id;

    private Order order;

    private ProductVariant variant;

    private Integer quantity;

    private BigDecimal unitPrice;

    private LocalDateTime createdAt;

    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
