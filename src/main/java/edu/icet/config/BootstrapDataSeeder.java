package edu.icet.config;

import edu.icet.model.entity.Role;
import edu.icet.model.entity.User;
import edu.icet.repository.RoleRepository;
import edu.icet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Bootstrap data seeder that runs on application startup.
 *
 * Creates default roles and the initial OWNER/super-admin user
 * so the system can be accessed for the first time.
 *
 * This is the industry-standard approach for solving the "chicken and egg"
 * problem where you need an admin to create users, but have no admin yet.
 *
 * Behavior:
 * - ALWAYS ensures required roles exist (OWNER, ADMIN, CASHIER)
 * - Creates bootstrap owner ONLY if no users exist in the database
 * - Credentials come from environment variables (secure, not hardcoded)
 * - Logs actions clearly (without exposing passwords)
 */
@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class BootstrapDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Bootstrap owner credentials from environment variables
    @Value("${app.bootstrap.owner.username:owner}")
    private String ownerUsername;

    @Value("${app.bootstrap.owner.email:owner@threadline.local}")
    private String ownerEmail;

    @Value("${app.bootstrap.owner.password:#{null}}")
    private String ownerPassword;

    // Default roles that must exist in the system
    private static final List<String> REQUIRED_ROLES = Arrays.asList(
            "OWNER",   // Super admin - full access, can create admins
            "ADMIN",   // System operator - manages catalog, inventory, users
            "CASHIER"  // POS user - sales operations
    );

    @Override
    @Transactional
    public void run(String... args) {
        log.info("=== Bootstrap Data Seeder Starting ===");

        // Step 1: Ensure all required roles exist
        ensureRolesExist();

        // Step 2: Create bootstrap owner if no users exist
        createBootstrapOwnerIfNeeded();

        log.info("=== Bootstrap Data Seeder Complete ===");
    }

    /**
     * Ensures all required roles exist in the database.
     * This runs every startup - safe because it only creates missing roles.
     */
    private void ensureRolesExist() {
        for (String roleName : REQUIRED_ROLES) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                log.info("Created role: {}", roleName);
            } else {
                log.debug("Role already exists: {}", roleName);
            }
        }
    }

    /**
     * Creates the initial OWNER user if no users exist in the system.
     *
     * This solves the bootstrap problem: you need an admin to create users,
     * but you can't log in without a user. This creates the first user
     * automatically on first run.
     *
     * Security notes:
     * - Password MUST be provided via environment variable
     * - If no password is configured, bootstrap is skipped (fail-safe)
     * - Password is never logged
     * - In production, change this password immediately after first login
     */
    private void createBootstrapOwnerIfNeeded() {
        // Only create if database has no users at all
        if (userRepository.count() > 0) {
            log.info("Users already exist in database - skipping bootstrap owner creation");
            return;
        }

        // Fail-safe: require password to be explicitly configured
        if (ownerPassword == null || ownerPassword.isBlank()) {
            log.warn("╔════════════════════════════════════════════════════════════════╗");
            log.warn("║  NO BOOTSTRAP OWNER CREATED - PASSWORD NOT CONFIGURED          ║");
            log.warn("║                                                                ║");
            log.warn("║  To create the initial owner account, set environment variable:║");
            log.warn("║  APP_BOOTSTRAP_OWNER_PASSWORD=<your-secure-password>           ║");
            log.warn("║                                                                ║");
            log.warn("║  Optional (have defaults):                                     ║");
            log.warn("║  APP_BOOTSTRAP_OWNER_USERNAME=owner                            ║");
            log.warn("║  APP_BOOTSTRAP_OWNER_EMAIL=owner@threadline.local              ║");
            log.warn("╚════════════════════════════════════════════════════════════════╝");
            return;
        }

        // Get the OWNER role
        Role ownerRole = roleRepository.findByName("ROLE_OWNER")
                .orElseThrow(() -> new IllegalStateException("ROLE_OWNER not found after seeding"));

        // Also give ADMIN role so owner can do everything
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found after seeding"));

        // Create the bootstrap owner
        User owner = new User();
        owner.setUsername(ownerUsername);
        owner.setEmail(ownerEmail);
        owner.setPassword(passwordEncoder.encode(ownerPassword));
        owner.setEnabled(true);
        owner.setRoles(new HashSet<>(Set.of(ownerRole, adminRole)));

        userRepository.save(owner);

        log.info("╔════════════════════════════════════════════════════════════════╗");
        log.info("║  BOOTSTRAP OWNER CREATED SUCCESSFULLY                          ║");
        log.info("║                                                                ║");
        log.info("║  Username: {}", String.format("%-45s║", ownerUsername));
        log.info("║  Email:    {}", String.format("%-45s║", ownerEmail));
        log.info("║  Roles:    OWNER, ADMIN                                        ║");
        log.info("║                                                                ║");
        log.info("║  ⚠️  IMPORTANT: Change this password after first login!        ║");
        log.info("╚════════════════════════════════════════════════════════════════╝");
    }
}
