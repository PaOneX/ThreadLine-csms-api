package edu.icet.controller;

import edu.icet.model.dto.auth.ApiResponse;
import edu.icet.model.dto.auth.LoginRequest;
import edu.icet.model.dto.auth.RefreshTokenRequest;
import edu.icet.model.dto.auth.TokenResponse;
import edu.icet.model.dto.user.ChangePasswordRequest;
import edu.icet.model.dto.user.UpdateProfileRequest;
import edu.icet.model.dto.user.UserDto;
import edu.icet.model.entity.RefreshToken;
import edu.icet.model.entity.Role;
import edu.icet.model.entity.User;
import edu.icet.repository.RefreshTokenRepository;
import edu.icet.repository.UserRepository;
import edu.icet.service.ProfileService;
import edu.icet.util.CookieUtil;
import edu.icet.util.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String ERR_USER_DISABLED = "USER_DISABLED";
    private static final String MSG_USER_DISABLED = "User account is disabled";

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final ProfileService profileService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @Valid @RequestBody LoginRequest req,
            HttpServletResponse response
    ) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            // Username from the authenticated principal (safe, no cast needed)
            String username = auth.getName();

            // Load DB user to check enabled and roles (also ensures we have current roles)
            User userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User record not found"));

            if (userEntity.getEnabled() != null && !userEntity.getEnabled()) {
                CookieUtil.deleteAuthCookies(response);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error(403, MSG_USER_DISABLED, ERR_USER_DISABLED));
            }

            Set<String> roles = userEntity.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            String accessToken = jwtService.generateAccessToken(username, roles);
            String refreshTokenString = jwtService.createRefreshTokenString();

            refreshTokenRepository.findByUser(userEntity).ifPresent(refreshTokenRepository::delete);

            RefreshToken refreshToken = RefreshToken.builder()
                    .token(refreshTokenString)
                    .user(userEntity)
                    .expiryDate(Instant.now().plusSeconds(jwtService.getRefreshTokenExpirySeconds()))
                    .build();
            refreshTokenRepository.save(refreshToken);

            CookieUtil.addAccessTokenCookie(response, accessToken, (int) jwtService.getAccessTokenExpirySeconds());
            CookieUtil.addRefreshTokenCookie(response, refreshTokenString, (int) jwtService.getRefreshTokenExpirySeconds());

            TokenResponse payload = new TokenResponse(
                    "Bearer",
                    accessToken,
                    jwtService.getAccessTokenExpirySeconds(),
                    refreshTokenString,
                    roles
            );

            return ResponseEntity.ok(ApiResponse.success(200, "Login successful", payload));
        } catch (DisabledException ex) {
            CookieUtil.deleteAuthCookies(response);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(403, MSG_USER_DISABLED, ERR_USER_DISABLED));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(401, "Invalid username or password", "INVALID_CREDENTIALS"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(401, "User not found", "USER_NOT_FOUND"));
        }
    }

    @PostMapping("/refresh")
    @Transactional
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(
            @Valid @RequestBody(required = false) RefreshTokenRequest req,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshTokenString = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshTokenString = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshTokenString == null && req != null) {
            refreshTokenString = req.getRefreshToken();
        }

        if (refreshTokenString == null) {
            CookieUtil.deleteAuthCookies(response);
            ApiResponse<TokenResponse> body = ApiResponse.error(400, "Refresh token required", "MISSING_REFRESH_TOKEN");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }

        // Server-side validation ensures token hasn't been revoked
        final String finalRefreshTokenString = refreshTokenString;
        return refreshTokenRepository.findByToken(finalRefreshTokenString)
                .map((RefreshToken oldRefreshToken) -> {
                    if (oldRefreshToken.getExpiryDate() != null && oldRefreshToken.getExpiryDate().isBefore(Instant.now())) {
                        refreshTokenRepository.delete(oldRefreshToken);
                        CookieUtil.deleteAuthCookies(response);
                        ApiResponse<TokenResponse> body = ApiResponse.error(401, "Refresh token expired", "REFRESH_TOKEN_EXPIRED");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
                    }

                    User user = oldRefreshToken.getUser();
                    if (user == null) {
                        refreshTokenRepository.delete(oldRefreshToken);
                        CookieUtil.deleteAuthCookies(response);
                        ApiResponse<TokenResponse> body = ApiResponse.error(401, "Invalid refresh token", "INVALID_REFRESH_TOKEN");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
                    }

                    if (user.getEnabled() != null && !user.getEnabled()) {
                        refreshTokenRepository.delete(oldRefreshToken);
                        CookieUtil.deleteAuthCookies(response);
                        ApiResponse<TokenResponse> body = ApiResponse.error(403, MSG_USER_DISABLED, ERR_USER_DISABLED);
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
                    }

                    Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
                    String newAccessToken = jwtService.generateAccessToken(user.getUsername(), roles);

                    String newRefreshTokenString = jwtService.createRefreshTokenString();

                    refreshTokenRepository.delete(oldRefreshToken);

                    RefreshToken newRefreshToken = RefreshToken.builder()
                            .token(newRefreshTokenString)
                            .user(user)
                            .expiryDate(Instant.now().plusSeconds(jwtService.getRefreshTokenExpirySeconds()))
                            .build();
                    refreshTokenRepository.save(newRefreshToken);

                    CookieUtil.addAccessTokenCookie(response, newAccessToken, (int) jwtService.getAccessTokenExpirySeconds());
                    CookieUtil.addRefreshTokenCookie(response, newRefreshTokenString, (int) jwtService.getRefreshTokenExpirySeconds());

                    TokenResponse payload = new TokenResponse(
                            "Bearer",
                            newAccessToken,
                            jwtService.getAccessTokenExpirySeconds(),
                            newRefreshTokenString,  // ← NEW refresh token!
                            roles
                    );

                    return ResponseEntity.ok(ApiResponse.success(200, "Token refreshed", payload));
                })
                .orElseGet(() -> {
                    CookieUtil.deleteAuthCookies(response);
                    ApiResponse<TokenResponse> body = ApiResponse.error(401, "Invalid refresh token", "INVALID_REFRESH_TOKEN");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
                });
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody(required = false) RefreshTokenRequest req,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshTokenString = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshTokenString = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshTokenString == null && req != null) {
            refreshTokenString = req.getRefreshToken();
        }

        if (refreshTokenString != null) {
            refreshTokenRepository.findByToken(refreshTokenString)
                    .ifPresent(refreshTokenRepository::delete);
        }

        CookieUtil.deleteAuthCookies(response);

        return ResponseEntity.ok(ApiResponse.success(200, "Logged out", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUserProfile(Principal principal) {
        UserDto profile = profileService.getCurrentUserProfile(principal.getName());
        return ResponseEntity.ok(ApiResponse.success(200, "Profile retrieved", profile));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> updateProfile(
            Principal principal,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        UserDto updated = profileService.updateProfile(principal.getName(), request);
        return ResponseEntity.ok(ApiResponse.success(200, "Profile updated", updated));
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            Principal principal,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        profileService.changePassword(principal.getName(), request);
        return ResponseEntity.ok(ApiResponse.success(200, "Password changed successfully", null));
    }
}
