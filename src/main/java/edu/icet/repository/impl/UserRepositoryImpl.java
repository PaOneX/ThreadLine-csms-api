package edu.icet.repository.impl;

import edu.icet.model.entity.User;
import edu.icet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public boolean existsByUsernameIgnoreCase(String username) {
        return false;
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return false;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        user.onCreate();
        String sql = "INSERT INTO `users` (`email`,`username`,`full_name`,`gender`,`password`,`enabled`) VALUES (?,?,?,?,?,?)";
        int result = jdbcTemplate.update(sql,
                user.getEmail(),
                user.getUsername(),
                user.getFullName(),
                user.getGender().name(),
                user.getPassword(),
                user.getEnabled()
        );
        return result > 0 ? user : null;
    }

    @Override
    public boolean existsById(Long userId) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public int count() {
        return 0;
    }
}
