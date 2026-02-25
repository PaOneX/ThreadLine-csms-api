package edu.icet.repository.impl;

import edu.icet.model.entity.Category;
import edu.icet.repository.CategoryRepository;
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
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Category> categoryRowMapper = (rs, rowNum) -> {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            category.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            category.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return category;
    };

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, categoryRowMapper);
    }

    @Override
    public Optional<Category> findById(Long id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        List<Category> categories = jdbcTemplate.query(sql, categoryRowMapper, id);
        return categories.isEmpty() ? Optional.empty() : Optional.of(categories.get(0));
    }

    @Override
    public Category save(Category entity) {
        if (entity.getId() == null) {
            // INSERT
            entity.onCreate();
            String sql = "INSERT INTO categories (name, description, created_at, updated_at) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getDescription());
                ps.setTimestamp(3, Timestamp.valueOf(entity.getCreatedAt()));
                ps.setTimestamp(4, Timestamp.valueOf(entity.getUpdatedAt()));
                return ps;
            }, keyHolder);
            if (result > 0 && keyHolder.getKey() != null) {
                entity.setId(keyHolder.getKey().longValue());
                return entity;
            }
            return null;
        } else {
            // UPDATE
            entity.onUpdate();
            String sql = "UPDATE categories SET name = ?, description = ?, updated_at = ? WHERE id = ?";
            int result = jdbcTemplate.update(sql,
                    entity.getName(),
                    entity.getDescription(),
                    Timestamp.valueOf(entity.getUpdatedAt()),
                    entity.getId());
            return result > 0 ? entity : null;
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM categories WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
