package edu.icet.controller;

import edu.icet.model.dto.auth.ApiResponse;
import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.dto.user.UpdateUserRolesRequest;
import edu.icet.model.dto.user.UserDto;
import edu.icet.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = adminUserService.getAllUsers();
        return ResponseEntity.ok(
                ApiResponse.success(200, "Users retrieved successfully", users)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = adminUserService.getUserById(id);
        return ResponseEntity.ok(
                ApiResponse.success(200, "User retrieved successfully", user)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody AdminCreateUserRequest request) {
        UserDto createdUser = adminUserService.createUser(request);
        URI location = URI.create("/api/admin/users/" + createdUser.getId());
        return ResponseEntity.created(location).body(
                ApiResponse.success(201, "User created successfully", createdUser)
        );
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<UserDto>> updateUserRoles(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRolesRequest request
    ) {
        UserDto updatedUser = adminUserService.updateUserRoles(id, request);
        return ResponseEntity.ok(
                ApiResponse.success(200, "User roles updated successfully", updatedUser)
        );
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<UserDto>> enableUser(@PathVariable Long id) {
        UserDto updatedUser = adminUserService.enableUser(id);
        return ResponseEntity.ok(
                ApiResponse.success(200, "User enabled successfully", updatedUser)
        );
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<UserDto>> disableUser(@PathVariable Long id) {
        UserDto updatedUser = adminUserService.disableUser(id);
        return ResponseEntity.ok(
                ApiResponse.success(200, "User disabled successfully", updatedUser)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.ok(
                ApiResponse.success(200, "User deleted successfully", null)
        );
    }
}