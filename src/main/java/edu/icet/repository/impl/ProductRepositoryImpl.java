package edu.icet.repository.impl;

import edu.icet.model.entity.Category;
import edu.icet.model.entity.Product;
import edu.icet.repository.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setImageUrl(rs.getString("image_url"));

        // Set category reference
        Long categoryId = rs.getObject("category_id", Long.class);
        if (categoryId != null) {
            Category category = new Category();
            category.setId(categoryId);
            product.setCategory(category);
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            product.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            product.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return product;
    };

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, productRowMapper, id);
        return products.isEmpty() ? Optional.empty() : Optional.of(products.get(0));
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            // INSERT
            product.onCreate();
            String sql = "INSERT INTO products (name, description, image_url, category_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getName());
                ps.setString(2, product.getDescription());
                ps.setString(3, product.getImageUrl());
                if (product.getCategory() != null) {
                    ps.setLong(4, product.getCategory().getId());
                } else {
                    ps.setNull(4, java.sql.Types.BIGINT);
                }
                ps.setTimestamp(5, Timestamp.valueOf(product.getCreatedAt()));
                ps.setTimestamp(6, Timestamp.valueOf(product.getUpdatedAt()));
                return ps;
            }, keyHolder);
            if (result > 0 && keyHolder.getKey() != null) {
                product.setId(keyHolder.getKey().longValue());
                return product;
            }
            return null;
        } else {
            // UPDATE
            product.onUpdate();
            String sql = "UPDATE products SET name = ?, description = ?, image_url = ?, category_id = ?, updated_at = ? WHERE id = ?";
            Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
            int result = jdbcTemplate.update(sql,
                    product.getName(),
                    product.getDescription(),
                    product.getImageUrl(),
                    categoryId,
                    Timestamp.valueOf(product.getUpdatedAt()),
                    product.getId());
            return result > 0 ? product : null;
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM products WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
