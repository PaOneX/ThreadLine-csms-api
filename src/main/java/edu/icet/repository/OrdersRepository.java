package edu.icet.repository;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.User;
import edu.icet.model.enums.OrderStatus;

import java.util.List;

public interface OrdersRepository {
    List<Order> findByUser(User user);

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);
}
