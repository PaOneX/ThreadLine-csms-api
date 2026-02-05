package edu.icet.model.entity;

import edu.icet.model.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "items"})
public class Order {
    private Long id;

    private User user;

    private OrderStatus status;

    private BigDecimal totalAmount;

    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    private LocalDateTime orderDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
        if (status == null) {
            status = OrderStatus.PENDING;
        }
    }

    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
