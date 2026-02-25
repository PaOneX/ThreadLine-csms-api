package edu.icet.repository;

import edu.icet.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAll(int offset, int limit, String sortBy, String direction, String search);

    long count(String search);

    User save(User user);

    void update(User user);

    void deleteById(Long id);

    void enableUser(Long id);

    void disableUser(Long id);

    List<String> findRolesByUserId(Long userId);

    void updateUserRoles(Long userId, List<Long> roleIds);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);
}
