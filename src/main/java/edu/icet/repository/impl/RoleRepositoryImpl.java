package edu.icet.repository.impl;

import edu.icet.model.entity.Role;
import edu.icet.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Optional<Role> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public void save(Role role) {

    }
}
