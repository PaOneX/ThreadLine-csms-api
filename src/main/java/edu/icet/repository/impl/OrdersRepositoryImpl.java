package edu.icet.repository.impl;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.User;
import edu.icet.model.enums.OrderStatus;
import edu.icet.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Order> findByUser(User user) {
        return List.of();
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return List.of();
    }

    @Override
    public Optional<Order> findById() {
        return Optional.empty();
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return null;
    }

    @Override
    public void save(Order order) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }
}
