 package edu.icet.config;

import edu.icet.util.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String API_ANY = "/api/**";
    private static final String ADMIN = "ADMIN";
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // Stateless APIs should not create/consume HTTP sessions.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Ensure 401/403 responses are JSON (useful for Swagger/Postman and SPA clients).
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint((req, res, ex) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json");
                            res.setCharacterEncoding(StandardCharsets.UTF_8.name());
                            res.getWriter().write("{\"error\":\"unauthorized\",\"message\":\"Authentication required\"}");
                        })
                        .accessDeniedHandler((req, res, ex) -> {
                            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            res.setContentType("application/json");
                            res.setCharacterEncoding(StandardCharsets.UTF_8.name());
                            res.getWriter().write("{\"error\":\"forbidden\",\"message\":\"Access denied\"}");
                        })
                )

                .authorizeHttpRequests(auth -> auth
                        // Public auth endpoints (login/refresh/logout) - but NOT /auth/me
                        .requestMatchers("/auth/login", "/auth/refresh", "/auth/logout").permitAll()

                        // /auth/me endpoints require authentication (any authenticated user)
                        .requestMatchers("/auth/me/**").authenticated()

                        // Swagger / OpenAPI endpoints are public to allow easy manual testing.
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Example of a single public API endpoint.
                        .requestMatchers(HttpMethod.POST, "/api/orders").permitAll()

                        // Restrict write operations on API endpoints to ADMIN.
                        .requestMatchers(HttpMethod.POST, API_ANY).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, API_ANY).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_ANY).hasRole(ADMIN)

                        // All other requests require authentication.
                        .anyRequest().authenticated()
                )

                // JWT filter must run before UsernamePasswordAuthenticationFilter so SecurityContext is populated early.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS configuration allowing cookies (credentials) from frontend.
     *
     * <p>Required for cookie-based authentication to work with SPA frontends.
     * <p>In production, restrict {@code allowedOrigins} to your actual frontend domain.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
