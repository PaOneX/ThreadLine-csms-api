package edu.icet.config;

import org.springframework.context.annotation.Configuration;

/**
 * CORS is configured in {@link SecurityConfig#corsConfigurationSource()}.
 * Having both a WebMvcConfigurer CORS config and a Security CORS config can cause confusing,
 * environment-dependent behavior. We keep a single source of truth in SecurityConfig.
 */
@Configuration
public class WebConfig {
    // Intentionally empty.
}
