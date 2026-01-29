package edu.icet.service;

import edu.icet.model.dto.UserDTO;
import edu.icet.model.dto.UserRequestDto;

import java.util.List;

public interface UserService {
    void addUser(UserRequestDto userRequestDto);
    void updateUser(Integer id, UserRequestDto userRequestDto);
    void deleteUser(Integer id);
    List<UserDTO> getUsers();
    UserDTO getUserById(Integer id);
}
