package edu.icet.repository;

import edu.icet.model.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    List<Role> findAll();

    Role save(Role role);

    Optional<Role> findById(Long id);

    List<String> findAllRoleNames();

    Long findRoleIdByName(String name);
}