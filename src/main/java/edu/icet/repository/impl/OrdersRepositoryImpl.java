package edu.icet.repository.impl;

import edu.icet.model.entity.Order;
import edu.icet.model.entity.User;
import edu.icet.model.enums.OrderStatus;
import edu.icet.repository.OrdersRepository;
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
public class OrdersRepositoryImpl implements OrdersRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Order> orderRowMapper = (rs, rowNum) -> {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));

        String statusStr = rs.getString("status");
        if (statusStr != null) {
            order.setStatus(OrderStatus.valueOf(statusStr));
        }

        // Set user reference
        Long userId = rs.getObject("user_id", Long.class);
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            order.setUser(user);
        }

        Timestamp orderDate = rs.getTimestamp("order_date");
        if (orderDate != null) {
            order.setOrderDate(orderDate.toLocalDateTime());
        }
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            order.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            order.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return order;
    };

    @Override
    public List<Order> findByUser(User user) {
        return findByUserId(user.getId());
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        return jdbcTemplate.query(sql, orderRowMapper, userId);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        String sql = "SELECT * FROM orders WHERE status = ?";
        return jdbcTemplate.query(sql, orderRowMapper, status.name());
    }

    @Override
    public Optional<Order> findById() {
        // This method seems to be a mistake in the interface - returning empty
        return Optional.empty();
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        List<Order> orders = jdbcTemplate.query(sql, orderRowMapper, orderId);
        return orders.isEmpty() ? Optional.empty() : Optional.of(orders.get(0));
    }

    @Override
    public void save(Order order) {
        if (order.getId() == null) {
            // INSERT
            order.onCreate();
            String sql = "INSERT INTO orders (user_id, status, total_amount, order_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, order.getUser().getId());
                ps.setString(2, order.getStatus().name());
                ps.setBigDecimal(3, order.getTotalAmount());
                ps.setTimestamp(4, Timestamp.valueOf(order.getOrderDate()));
                ps.setTimestamp(5, Timestamp.valueOf(order.getCreatedAt()));
                ps.setTimestamp(6, Timestamp.valueOf(order.getUpdatedAt()));
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                order.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            order.onUpdate();
            String sql = "UPDATE orders SET user_id = ?, status = ?, total_amount = ?, order_date = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    order.getUser().getId(),
                    order.getStatus().name(),
                    order.getTotalAmount(),
                    Timestamp.valueOf(order.getOrderDate()),
                    Timestamp.valueOf(order.getUpdatedAt()),
                    order.getId());
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM orders WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, orderRowMapper);
    }
}
