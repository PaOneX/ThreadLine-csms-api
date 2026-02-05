package edu.icet.repository;

import edu.icet.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    // Pagination with search and filter
    Page<User> findUsersByCriteria(
            String username,
            String email,
            Boolean enabled,
            Pageable pageable
    );

    // Filter by role
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
}
