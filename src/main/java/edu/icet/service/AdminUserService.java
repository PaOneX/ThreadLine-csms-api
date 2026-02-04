package edu.icet.service;

import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.dto.user.UpdateUserRolesRequest;
import edu.icet.model.dto.user.UserDto;

import java.util.List;

public interface AdminUserService {

    UserDto createUser(AdminCreateUserRequest request);

    UserDto updateUserRoles(Long userId, UpdateUserRolesRequest request);

    UserDto enableUser(Long userId);
    UserDto disableUser(Long userId);

    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
    void deleteUser(Long userId);
}
