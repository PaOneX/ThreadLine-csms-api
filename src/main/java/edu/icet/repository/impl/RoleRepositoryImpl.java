package edu.icet.repository.impl;

import edu.icet.model.entity.Role;
import edu.icet.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Role> roleRowMapper = (rs, rowNum) -> {
        Role role = new Role();
        role.setId(rs.getLong("id"));
        role.setName(rs.getString("name"));
        return role;
    };

    @Override
    public Optional<Role> findByName(String name) {
        String sql = "SELECT * FROM roles WHERE name = ?";
        List<Role> roles = jdbcTemplate.query(sql, roleRowMapper, name);
        return roles.stream().findFirst();
    }

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM roles WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    @Override
    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        return jdbcTemplate.query(sql, roleRowMapper);
    }

    @Override
    public Role save(Role role) {
        String sql = "INSERT INTO roles (name) VALUES (?)";
        jdbcTemplate.update(sql, role.getName());
        return findByName(role.getName()).orElseThrow();
    }

    @Override
    public Optional<Role> findById(Long id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        List<Role> roles = jdbcTemplate.query(sql, roleRowMapper, id);
        return roles.stream().findFirst();
    }

    @Override
    public List<String> findAllRoleNames() {
        String sql = "SELECT name FROM roles";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public Long findRoleIdByName(String name) {
        String sql = "SELECT id FROM roles WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, name);
    }
}
