package edu.icet.controller;

import edu.icet.model.dto.Page;
import edu.icet.model.dto.PageResponse;
import edu.icet.model.dto.auth.ApiResponse;
import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.dto.user.UpdateUserRolesRequest;
import edu.icet.model.dto.user.UserDto;
import edu.icet.service.AdminUserService;
import edu.icet.util.pagination.PageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','OWNER')")
public class UserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserDto>>> findUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "username") String sort,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) String search
    ) {
        PageRequest pageRequest = new PageRequest(page, size, sort, direction);
        Page<UserDto> users = adminUserService.getUsers(pageRequest, search);
        PageResponse<UserDto> pageResponse = PageResponse.of(users);
        return ResponseEntity.ok(
                ApiResponse.success(200, "Users retrieved successfully", pageResponse)
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