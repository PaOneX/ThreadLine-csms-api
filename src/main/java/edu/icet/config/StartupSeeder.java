package edu.icet.config;

import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.enums.Gender;
import edu.icet.service.AdminUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StartupSeeder {
    private final AdminUserService adminUserService;

    public StartupSeeder(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PostConstruct
    public void seedAdminUser() {
        if (!adminUserService.existsByUsernameIgnoreCase("admin")) {
            AdminCreateUserRequest request = new AdminCreateUserRequest();
            request.setUsername("admin");
            request.setEmail("admin@yourdomain.com");
            request.setFullName("System Administrator");
            request.setGender(Gender.MALE);
            request.setPassword("admin123");
            request.setRoles(Set.of("ROLE_ADMIN"));
            adminUserService.createUser(request);
        }
    }
}
