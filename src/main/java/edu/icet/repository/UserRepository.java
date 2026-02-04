package edu.icet.repository;

import edu.icet.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    // Pagination with search and filter
    @Query("SELECT u FROM User u WHERE " +
            "(:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
            "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:enabled IS NULL OR u.enabled = :enabled)")
    Page<User> findUsersByCriteria(
            @Param("username") String username,
            @Param("email") String email,
            @Param("enabled") Boolean enabled,
            Pageable pageable
    );

    // Filter by role
    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE " +
            "r.name = :roleName AND " +
            "(:enabled IS NULL OR u.enabled = :enabled)")
    Page<User> findByRoleAndEnabled(
            @Param("roleName") String roleName,
            @Param("enabled") Boolean enabled,
            Pageable pageable
    );
}
