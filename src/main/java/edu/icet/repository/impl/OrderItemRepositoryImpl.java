package edu.icet.repository.impl;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.OrderItem;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<OrderItem> orderItemRowMapper = (rs, rowNum) -> {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setUnitPrice(rs.getBigDecimal("unit_price"));

        // Set order reference
        Long orderId = rs.getObject("order_id", Long.class);
        if (orderId != null) {
            Order order = new Order();
            order.setId(orderId);
            orderItem.setOrder(order);
        }

        // Set variant reference
        Long variantId = rs.getObject("variant_id", Long.class);
        if (variantId != null) {
            ProductVariant variant = new ProductVariant();
            variant.setId(variantId);
            orderItem.setVariant(variant);
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            orderItem.setCreatedAt(createdAt.toLocalDateTime());
        }
        return orderItem;
    };

    @Override
    public List<OrderItem> findAllByOrder(Order order) {
        return findAllByOrderId(order.getId());
    }

    @Override
    public List<OrderItem> findAllByOrderId(Long orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        return jdbcTemplate.query(sql, orderItemRowMapper, orderId);
    }

    @Override
    public List<OrderItem> findAllByVariant(ProductVariant variant) {
        String sql = "SELECT * FROM order_items WHERE variant_id = ?";
        return jdbcTemplate.query(sql, orderItemRowMapper, variant.getId());
    }

    @Override
    public void save(OrderItem entity) {
        if (entity.getId() == null) {
            // INSERT
            entity.onCreate();
            String sql = "INSERT INTO order_items (order_id, variant_id, quantity, unit_price, created_at) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, entity.getOrder().getId());
                ps.setLong(2, entity.getVariant().getId());
                ps.setInt(3, entity.getQuantity());
                ps.setBigDecimal(4, entity.getUnitPrice());
                ps.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()));
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                entity.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            String sql = "UPDATE order_items SET order_id = ?, variant_id = ?, quantity = ?, unit_price = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    entity.getOrder().getId(),
                    entity.getVariant().getId(),
                    entity.getQuantity(),
                    entity.getUnitPrice(),
                    entity.getId());
        }
    }

    @Override
    public Optional<OrderItem> findById(Long itemId) {
        String sql = "SELECT * FROM order_items WHERE id = ?";
        List<OrderItem> items = jdbcTemplate.query(sql, orderItemRowMapper, itemId);
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

    @Override
    public void delete(OrderItem orderItem) {
        String sql = "DELETE FROM order_items WHERE id = ?";
        jdbcTemplate.update(sql, orderItem.getId());
    }
}
