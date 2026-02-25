package edu.icet.repository.impl;

import edu.icet.model.entity.Role;
import edu.icet.model.entity.User;
import edu.icet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setGender(rs.getString("gender") != null ? edu.icet.model.enums.Gender.valueOf(rs.getString("gender")) : null);
        user.setPassword(rs.getString("password"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        user.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        user.setRoles(new HashSet<>());
        return user;
    };

    private List<Role> findRolesForUser(Long userId) {
        String sql = "SELECT r.id, r.name FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            return role;
        }, userId);
    }

    private User attachRoles(User user) {
        user.setRoles(new HashSet<>(findRolesForUser(user.getId())));
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, id);
        return users.stream().findFirst().map(this::attachRoles);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, username);
        return users.stream().findFirst().map(this::attachRoles);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.stream().findFirst().map(this::attachRoles);
    }

    @Override
    public List<User> findAll(int offset, int limit, String sortBy, String direction, String search) {
        String sql = "SELECT * FROM users WHERE username LIKE ? OR email LIKE ? ORDER BY " + sortBy + " " + direction + " LIMIT ? OFFSET ?";
        String searchPattern = "%" + (search == null ? "" : search) + "%";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, searchPattern, searchPattern, limit, offset);
        users.forEach(u -> u.setRoles(new HashSet<>(findRolesForUser(u.getId()))));
        return users;
    }

    @Override
    public long count(String search) {
        String sql = "SELECT COUNT(*) FROM users WHERE username LIKE ? OR email LIKE ?";
        String searchPattern = "%" + (search == null ? "" : search) + "%";
        return jdbcTemplate.queryForObject(sql, Long.class, searchPattern, searchPattern);
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (username, email, full_name, password, created_at) VALUES (?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getFullName(), user.getPassword());
        User savedUser = findByUsername(user.getUsername()).orElseThrow();
        // Assign roles to user
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            String insertRoleSql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
            for (Role role : user.getRoles()) {
                jdbcTemplate.update(insertRoleSql, savedUser.getId(), role.getId());
            }
            // Refresh roles
            savedUser.setRoles(new HashSet<>(findRolesForUser(savedUser.getId())));
        }
        return savedUser;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void enableUser(Long id) {
        String sql = "UPDATE users SET enabled = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void disableUser(Long id) {
        String sql = "UPDATE users SET enabled = FALSE WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<String> findRolesByUserId(Long userId) {
        String sql = "SELECT r.name FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, userId);
    }

    @Override
    public void updateUserRoles(Long userId, List<Long> roleIds) {
        String deleteSql = "DELETE FROM user_roles WHERE user_id = ?";
        jdbcTemplate.update(deleteSql, userId);
        String insertSql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        for (Long roleId : roleIds) {
            jdbcTemplate.update(insertSql, userId, roleId);
        }
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
}
