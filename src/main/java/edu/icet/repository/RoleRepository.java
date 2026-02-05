package edu.icet.repository;

import edu.icet.model.entity.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}