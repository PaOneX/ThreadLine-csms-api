package edu.icet.repository.impl;

import edu.icet.model.entity.Inventory;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.InventoryRepository;
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

@RequiredArgsConstructor
@Repository
public class InventoryRepositoryImpl implements InventoryRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Inventory> inventoryRowMapper = (rs, rowNum) -> {
        Inventory inventory = new Inventory();
        inventory.setId(rs.getLong("id"));
        inventory.setQuantity(rs.getInt("quantity"));

        // Set variant reference
        Long variantId = rs.getObject("variant_id", Long.class);
        if (variantId != null) {
            ProductVariant variant = new ProductVariant();
            variant.setId(variantId);
            inventory.setVariant(variant);
        }

        Timestamp lastRestocked = rs.getTimestamp("last_restocked_at");
        if (lastRestocked != null) {
            inventory.setLastRestocked(lastRestocked.toLocalDateTime());
        }
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            inventory.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            inventory.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return inventory;
    };

    @Override
    public Optional<Inventory> findByVariant(ProductVariant variant) {
        return findByVariantId(variant.getId());
    }

    @Override
    public Optional<Inventory> findByVariantId(Long variantId) {
        String sql = "SELECT * FROM inventories WHERE variant_id = ?";
        List<Inventory> inventories = jdbcTemplate.query(sql, inventoryRowMapper, variantId);
        return inventories.isEmpty() ? Optional.empty() : Optional.of(inventories.get(0));
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM inventories WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void save(Inventory entity) {
        if (entity.getId() == null) {
            // INSERT
            entity.onCreate();
            String sql = "INSERT INTO inventories (variant_id, quantity, last_restocked_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, entity.getVariant().getId());
                ps.setInt(2, entity.getQuantity());
                if (entity.getLastRestocked() != null) {
                    ps.setTimestamp(3, Timestamp.valueOf(entity.getLastRestocked()));
                } else {
                    ps.setNull(3, java.sql.Types.TIMESTAMP);
                }
                ps.setTimestamp(4, Timestamp.valueOf(entity.getCreatedAt()));
                ps.setTimestamp(5, Timestamp.valueOf(entity.getUpdatedAt()));
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                entity.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            entity.onUpdate();
            String sql = "UPDATE inventories SET variant_id = ?, quantity = ?, last_restocked_at = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    entity.getVariant().getId(),
                    entity.getQuantity(),
                    entity.getLastRestocked() != null ? Timestamp.valueOf(entity.getLastRestocked()) : null,
                    Timestamp.valueOf(entity.getUpdatedAt()),
                    entity.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM inventories WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Inventory> findAll() {
        String sql = "SELECT * FROM inventories";
        return jdbcTemplate.query(sql, inventoryRowMapper);
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        String sql = "SELECT * FROM inventories WHERE id = ?";
        List<Inventory> inventories = jdbcTemplate.query(sql, inventoryRowMapper, id);
        return inventories.isEmpty() ? Optional.empty() : Optional.of(inventories.get(0));
    }
}
