package edu.icet.repository;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.User;
import edu.icet.model.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository {
    List<Order> findByUser(User user);

    List<Order> findByUserId(Long userId);
    Optional<Order> findById(Long userId);

    List<Order> findByStatus(OrderStatus status);

    Optional<Order> findById();

    void save(Order order);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Order> findAll();
}
