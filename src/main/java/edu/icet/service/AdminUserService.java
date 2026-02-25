package edu.icet.service;

import edu.icet.model.dto.Page;
import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.dto.user.UpdateUserRolesRequest;
import edu.icet.model.dto.user.UserDto;
import edu.icet.util.pagination.PageRequest;

public interface AdminUserService {

    UserDto createUser(AdminCreateUserRequest request);

    UserDto updateUserRoles(Long userId, UpdateUserRolesRequest request);

    UserDto enableUser(Long userId);

    UserDto disableUser(Long userId);

    Page<UserDto> getUsers(PageRequest pageRequest, String search);

    UserDto getUserById(Long userId);

    void deleteUser(Long userId);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);
}
