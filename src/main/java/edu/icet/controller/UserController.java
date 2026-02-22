package edu.icet.controller;

//import edu.icet.model.dto.PageResponse;
import edu.icet.model.dto.auth.ApiResponse;
import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.dto.user.UpdateUserRolesRequest;
import edu.icet.model.dto.user.UserDto;
import edu.icet.model.dto.user.UserSearchCriteria;
import edu.icet.service.AdminUserService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
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
//
//    @Operation(summary = "List users (admin)")
//    @Parameter(name = "page", in = ParameterIn.QUERY, required = false, description = "0-based page index (default 0)", schema = @Schema(type = "integer", minimum = "0"))
//    @Parameter(name = "size", in = ParameterIn.QUERY, required = false, description = "Page size (default 20)", schema = @Schema(type = "integer", minimum = "1"))
//    @Parameter(name = "sort", in = ParameterIn.QUERY, required = false, description = "Sort property (default username)", schema = @Schema(type = "string"))
//    @GetMapping
//    public ResponseEntity<ApiResponse<PageResponse<UserDto>>> findUsers(
//            @Parameter(hidden = true) UserSearchCriteria criteria,
//            @Parameter(hidden = true)
//            @PageableDefault(size = 20, sort = "username", direction = Sort.Direction.ASC) Pageable pageable
//    ) {
//        // pageable + criteria are both optional.
//        // /api/admin/users will work with defaults: page=0,size=20,sort=username,asc
//        Page<UserDto> users = adminUserService.findUsers(criteria, pageable);
//        PageResponse<UserDto> pageResponse = PageResponse.of(users);
//        return ResponseEntity.ok(
//                ApiResponse.success(200, "Users retrieved successfully", pageResponse)
//        );
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = adminUserService.getUserById(id);
        return ResponseEntity.ok(
                ApiResponse.success(200, "User retrieved successfully", user)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody AdminCreateUserRequest request) {
        UserDto createdUser = adminUserService.createUser(request);
        URI location = URI.create("/api/admin/users/" + createdUser.getId());
        return ResponseEntity.created(location).body(
                ApiResponse.success(201, "User created successfully", createdUser)
        );
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<UserDto>> updateUserRoles(
            @PathVariable Long id,
            @RequestBody UpdateUserRolesRequest request
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