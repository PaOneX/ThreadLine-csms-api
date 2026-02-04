package edu.icet.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {

    private final String tokenType;
    private final String accessToken;
    private final long accessTokenExpiresInSeconds;
    private final String refreshToken;
    private final Set<String> roles;
}
