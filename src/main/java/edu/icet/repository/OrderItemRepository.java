package edu.icet.repository;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.OrderItem;
import edu.icet.model.entity.ProductVariant;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItem> findAllByOrder(Order order);

    List<OrderItem> findAllByOrderId(Long orderId);

    List<OrderItem> findAllByVariant(ProductVariant variant);
}
