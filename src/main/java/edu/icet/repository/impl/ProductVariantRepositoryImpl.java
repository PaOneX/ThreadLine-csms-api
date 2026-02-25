package edu.icet.repository.impl;

import edu.icet.model.entity.Product;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.ProductVarientRpository;
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
public class ProductVariantRepositoryImpl implements ProductVarientRpository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ProductVariant> variantRowMapper = (rs, rowNum) -> {
        ProductVariant variant = new ProductVariant();
        variant.setId(rs.getLong("id"));
        variant.setSize(rs.getString("size"));
        variant.setColor(rs.getString("color"));
        variant.setPrice(rs.getBigDecimal("price"));
        variant.setSku(rs.getString("sku"));

        // Set product reference
        Long productId = rs.getObject("product_id", Long.class);
        if (productId != null) {
            Product product = new Product();
            product.setId(productId);
            variant.setProduct(product);
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            variant.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            variant.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return variant;
    };

    @Override
    public List<ProductVariant> findByColor(String color) {
        String sql = "SELECT * FROM product_variants WHERE color = ?";
        return jdbcTemplate.query(sql, variantRowMapper, color);
    }

    @Override
    public List<ProductVariant> findBySize(String size) {
        String sql = "SELECT * FROM product_variants WHERE size = ?";
        return jdbcTemplate.query(sql, variantRowMapper, size);
    }

    @Override
    public List<ProductVariant> findByProduct(Product product) {
        String sql = "SELECT * FROM product_variants WHERE product_id = ?";
        return jdbcTemplate.query(sql, variantRowMapper, product.getId());
    }

    @Override
    public Optional<ProductVariant> findByProductId(Long productId) {
        String sql = "SELECT * FROM product_variants WHERE product_id = ?";
        List<ProductVariant> variants = jdbcTemplate.query(sql, variantRowMapper, productId);
        return variants.isEmpty() ? Optional.empty() : Optional.of(variants.get(0));
    }

    @Override
    public Optional<ProductVariant> findById(Long variantId) {
        String sql = "SELECT * FROM product_variants WHERE id = ?";
        List<ProductVariant> variants = jdbcTemplate.query(sql, variantRowMapper, variantId);
        return variants.isEmpty() ? Optional.empty() : Optional.of(variants.get(0));
    }

    @Override
    public void save(ProductVariant variant) {
        if (variant.getId() == null) {
            // INSERT
            variant.onCreate();
            String sql = "INSERT INTO product_variants (product_id, size, color, price, sku, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, variant.getProduct().getId());
                ps.setString(2, variant.getSize());
                ps.setString(3, variant.getColor());
                ps.setBigDecimal(4, variant.getPrice());
                ps.setString(5, variant.getSku());
                ps.setTimestamp(6, Timestamp.valueOf(variant.getCreatedAt()));
                ps.setTimestamp(7, Timestamp.valueOf(variant.getUpdatedAt()));
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                variant.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            variant.onUpdate();
            String sql = "UPDATE product_variants SET product_id = ?, size = ?, color = ?, price = ?, sku = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    variant.getProduct().getId(),
                    variant.getSize(),
                    variant.getColor(),
                    variant.getPrice(),
                    variant.getSku(),
                    Timestamp.valueOf(variant.getUpdatedAt()),
                    variant.getId());
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM product_variants WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product_variants WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ProductVariant> findAll() {
        String sql = "SELECT * FROM product_variants";
        return jdbcTemplate.query(sql, variantRowMapper);
    }
}
