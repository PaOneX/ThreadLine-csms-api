package edu.icet.repository;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.OrderItem;
import edu.icet.model.entity.ProductVariant;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {
    List<OrderItem> findAllByOrder(Order order);

    List<OrderItem> findAllByOrderId(Long orderId);

    List<OrderItem> findAllByVariant(ProductVariant variant);

    void save(OrderItem entity);

    Optional<OrderItem> findById(Long itemId);

    void delete(OrderItem orderItem);
}
