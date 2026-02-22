package edu.icet.repository.impl;

import edu.icet.model.entity.Supplier;
import edu.icet.repository.SupplierRepository;
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
public class SupplierRepositoryImpl implements SupplierRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Supplier> supplierRowMapper = (rs, rowNum) -> {
        Supplier supplier = new Supplier();
        supplier.setId(rs.getLong("id"));
        supplier.setName(rs.getString("name"));
        supplier.setEmail(rs.getString("email"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setAddress(rs.getString("address"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            supplier.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            supplier.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return supplier;
    };

    @Override
    public void save(Supplier entity) {
        if (entity.getId() == null) {
            // INSERT
            entity.onCreate();
            String sql = "INSERT INTO suppliers (name, email, phone, address, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getEmail());
                ps.setString(3, entity.getPhone());
                ps.setString(4, entity.getAddress());
                ps.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()));
                ps.setTimestamp(6, Timestamp.valueOf(entity.getUpdatedAt()));
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                entity.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            entity.onUpdate();
            String sql = "UPDATE suppliers SET name = ?, email = ?, phone = ?, address = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPhone(),
                    entity.getAddress(),
                    Timestamp.valueOf(entity.getUpdatedAt()),
                    entity.getId());
        }
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        List<Supplier> suppliers = jdbcTemplate.query(sql, supplierRowMapper, id);
        return suppliers.isEmpty() ? Optional.empty() : Optional.of(suppliers.get(0));
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM suppliers WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Supplier> findAll() {
        String sql = "SELECT * FROM suppliers";
        return jdbcTemplate.query(sql, supplierRowMapper);
    }
}
