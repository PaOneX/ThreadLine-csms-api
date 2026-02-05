package edu.icet.repository;

import edu.icet.model.entity.RefreshToken;
import edu.icet.model.entity.User;

import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);
}
