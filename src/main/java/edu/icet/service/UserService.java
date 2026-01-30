package edu.icet.service;

import edu.icet.model.dto.UserDTO;
import edu.icet.model.dto.UserRequestDto;

import java.util.List;

public interface UserService {
    void addUser(UserRequestDto userRequestDto);

    void updateUser(Long id, UserRequestDto userRequestDto);

    void deleteUser(Long id);

    List<UserDTO> getUsers();

    UserDTO getUserById(Long id);
}
