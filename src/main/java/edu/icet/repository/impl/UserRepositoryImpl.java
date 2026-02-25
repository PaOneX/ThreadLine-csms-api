package edu.icet.repository.impl;

import edu.icet.model.dto.Page;
import edu.icet.model.dto.Pageable;
import edu.icet.model.entity.User;
import edu.icet.model.enums.Gender;
import edu.icet.repository.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setPassword(rs.getString("password"));
        user.setEnabled(rs.getBoolean("enabled"));

        String genderStr = rs.getString("gender");
        if (genderStr != null) {
            user.setGender(Gender.valueOf(genderStr));
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return user;
    };

    private static void appendWhere(StringBuilder sb, String field, Object value) {
        if (value != null && !value.toString().isEmpty()) {
            sb.append(" AND ").append(field).append(" LIKE ?");
        }
    }

    private static void appendWhereBool(StringBuilder sb, String field, Boolean value) {
        if (value != null) {
            sb.append(" AND ").append(field).append(" = ?");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, username);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public boolean existsByUsernameIgnoreCase(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE LOWER(username) = LOWER(?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE LOWER(email) = LOWER(?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public Page<User> findUsersByCriteria(String username, String email, Boolean enabled, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM users WHERE 1=1");
        appendWhere(sql, "username", username);
        appendWhere(countSql, "username", username);
        appendWhere(sql, "email", email);
        appendWhere(countSql, "email", email);
        appendWhereBool(sql, "enabled", enabled);
        appendWhereBool(countSql, "enabled", enabled);

        sql.append(" ORDER BY id DESC LIMIT ? OFFSET ?");

        java.util.List<Object> params = new java.util.ArrayList<>();
        java.util.List<Object> countParams = new java.util.ArrayList<>();
        if (username != null && !username.isEmpty()) {
            params.add("%" + username + "%");
            countParams.add("%" + username + "%");
        }
        if (email != null && !email.isEmpty()) {
            params.add("%" + email + "%");
            countParams.add("%" + email + "%");
        }
        if (enabled != null) {
            params.add(enabled);
            countParams.add(enabled);
        }
        params.add(pageable.getSize());
        params.add(pageable.getPage() * pageable.getSize());

        List<User> users = jdbcTemplate.query(sql.toString(), userRowMapper, params.toArray());
        Integer totalElements = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, countParams.toArray());
        int totalPages = (int) Math.ceil((totalElements != null ? totalElements : 0) / (double) pageable.getSize());
        return new Page<>(users, totalPages, totalElements != null ? totalElements : 0, pageable.getSize(), pageable.getPage());
    }

    @Override
    public Page<User> findByRoleAndEnabled(String roleName, Boolean enabled, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT u.* FROM users u JOIN user_roles ur ON u.id = ur.user_id JOIN roles r ON ur.role_id = r.id WHERE r.name = ?");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM users u JOIN user_roles ur ON u.id = ur.user_id JOIN roles r ON ur.role_id = r.id WHERE r.name = ?");
        java.util.List<Object> params = new java.util.ArrayList<>();
        java.util.List<Object> countParams = new java.util.ArrayList<>();
        params.add(roleName);
        countParams.add(roleName);
        if (enabled != null) {
            sql.append(" AND u.enabled = ?");
            countSql.append(" AND u.enabled = ?");
            params.add(enabled);
            countParams.add(enabled);
        }
        sql.append(" ORDER BY u.id DESC LIMIT ? OFFSET ?");
        params.add(pageable.getSize());
        params.add(pageable.getPage() * pageable.getSize());
        List<User> users = jdbcTemplate.query(sql.toString(), userRowMapper, params.toArray());
        Integer totalElements = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, countParams.toArray());
        int totalPages = (int) Math.ceil((totalElements != null ? totalElements : 0) / (double) pageable.getSize());
        return new Page<>(users, totalPages, totalElements != null ? totalElements : 0, pageable.getSize(), pageable.getPage());
    }

    @Override
    public Optional<User> findById(Long userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            // INSERT
            user.onCreate();
            String sql = "INSERT INTO users (email, username, full_name, gender, password, enabled, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getFullName());
                ps.setString(4, user.getGender() != null ? user.getGender().name() : null);
                ps.setString(5, user.getPassword());
                ps.setBoolean(6, user.getEnabled() != null ? user.getEnabled() : true);
                ps.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
                ps.setTimestamp(8, Timestamp.valueOf(user.getUpdatedAt()));
                return ps;
            }, keyHolder);
            if (result > 0 && keyHolder.getKey() != null) {
                user.setId(keyHolder.getKey().longValue());
                return user;
            }
            return null;
        } else {
            // UPDATE
            user.onUpdate();
            String sql = "UPDATE users SET email = ?, username = ?, full_name = ?, gender = ?, password = ?, enabled = ?, updated_at = ? WHERE id = ?";
            int result = jdbcTemplate.update(sql,
                    user.getEmail(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getGender() != null ? user.getGender().name() : null,
                    user.getPassword(),
                    user.getEnabled(),
                    Timestamp.valueOf(user.getUpdatedAt()),
                    user.getId());
            return result > 0 ? user : null;
        }
    }

    @Override
    public boolean existsById(Long userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM users";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }
}
