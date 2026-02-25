package edu.icet.repository;

import edu.icet.model.dto.Page;
import edu.icet.model.dto.Pageable;
import edu.icet.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    Page<User> findUsersByCriteria(
            String username,
            String email,
            Boolean enabled,
            Pageable pageable
    );

    Page<User> findByRoleAndEnabled(
            String roleName,
            Boolean enabled,
            Pageable pageable
    );

    Optional<User> findById(Long userId);

    User save(User user);

    boolean existsById(Long userId);

    void deleteById(Long id);

    List<User> findAll();

    int count();
}
