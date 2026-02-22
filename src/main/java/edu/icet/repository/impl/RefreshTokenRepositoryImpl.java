package edu.icet.repository.impl;

import edu.icet.model.entity.RefreshToken;
import edu.icet.model.entity.User;
import edu.icet.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<RefreshToken> refreshTokenRowMapper = (rs, rowNum) -> {
        RefreshToken token = new RefreshToken();
        token.setId(rs.getLong("id"));
        token.setToken(rs.getString("token"));

        Timestamp expiryDate = rs.getTimestamp("expiry_date");
        if (expiryDate != null) {
            token.setExpiryDate(expiryDate.toInstant());
        }

        // Set user reference
        Long userId = rs.getObject("user_id", Long.class);
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            token.setUser(user);
        }

        return token;
    };

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        String sql = "SELECT * FROM refresh_tokens WHERE token = ?";
        List<RefreshToken> tokens = jdbcTemplate.query(sql, refreshTokenRowMapper, token);
        return tokens.isEmpty() ? Optional.empty() : Optional.of(tokens.get(0));
    }

    @Override
    public Optional<RefreshToken> findByUser(User user) {
        String sql = "SELECT * FROM refresh_tokens WHERE user_id = ?";
        List<RefreshToken> tokens = jdbcTemplate.query(sql, refreshTokenRowMapper, user.getId());
        return tokens.isEmpty() ? Optional.empty() : Optional.of(tokens.get(0));
    }

    public void save(RefreshToken refreshToken) {
        if (refreshToken.getId() == null) {
            // INSERT
            String sql = "INSERT INTO refresh_tokens (token, user_id, expiry_date, created_at) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, refreshToken.getToken());
                ps.setLong(2, refreshToken.getUser().getId());
                ps.setTimestamp(3, Timestamp.from(refreshToken.getExpiryDate()));
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                refreshToken.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            String sql = "UPDATE refresh_tokens SET token = ?, user_id = ?, expiry_date = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    refreshToken.getToken(),
                    refreshToken.getUser().getId(),
                    Timestamp.from(refreshToken.getExpiryDate()),
                    refreshToken.getId());
        }
    }

    public void deleteByUser(User user) {
        String sql = "DELETE FROM refresh_tokens WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getId());
    }

    public void deleteByToken(String token) {
        String sql = "DELETE FROM refresh_tokens WHERE token = ?";
        jdbcTemplate.update(sql, token);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM refresh_tokens WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

