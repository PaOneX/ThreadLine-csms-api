package edu.icet.service.impl;

import edu.icet.model.dto.UserDTO;
import edu.icet.repository.UserRepository;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public void addUser(UserDTO userDTO) {

    }

    @Override
    public void updateUser(UserDTO userDTO) {

    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public List<UserDTO> getUsers() {
        return List.of();
    }

    @Override
    public UserDTO getUserById(Integer id) {
        return null;
    }
}
