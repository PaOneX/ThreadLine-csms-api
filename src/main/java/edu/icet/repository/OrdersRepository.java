package edu.icet.repository;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.User;
import edu.icet.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository {
    List<Order> findByUser(User user);

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    Optional<Order> findById(@NotNull(message = "orderId must not be null") Long orderId);

    void save(Order order);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Order> findAll();
}
