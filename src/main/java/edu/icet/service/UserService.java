package edu.icet.service;

import edu.icet.model.dto.user.UserDto;
import edu.icet.model.dto.user.UserRequestDto;

import java.util.List;

public interface UserService {
    void addUser(UserRequestDto userRequestDto);

    void updateUser(Long id, UserRequestDto userRequestDto);

    void deleteUser(Long id);

    List<UserDto> getUsers();

    UserDto getUserById(Long id);
}
