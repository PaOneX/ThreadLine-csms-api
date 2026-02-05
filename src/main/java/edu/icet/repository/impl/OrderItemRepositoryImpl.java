package edu.icet.repository.impl;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.OrderItem;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<OrderItem> findAllByOrder(Order order) {
        return List.of();
    }

    @Override
    public List<OrderItem> findAllByOrderId(Long orderId) {
        return List.of();
    }

    @Override
    public List<OrderItem> findAllByVariant(ProductVariant variant) {
        return List.of();
    }

    @Override
    public void save(OrderItem entity) {

    }

    @Override
    public Optional<OrderItem> findById(Long itemId) {
        return Optional.empty();
    }

    @Override
    public void delete(OrderItem orderItem) {

    }
}
