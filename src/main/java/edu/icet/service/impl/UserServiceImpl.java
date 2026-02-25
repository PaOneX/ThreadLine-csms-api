package edu.icet.service.impl;

import edu.icet.mapper.UserMapper;
import edu.icet.model.dto.user.UserDto;
import edu.icet.model.dto.user.UserRequestDto;
import edu.icet.model.entity.User;
import edu.icet.repository.UserRepository;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addUser(UserRequestDto requestDto) {
        User user = mapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setEnabled(true);
        repository.save(user);
    }

    @Override
    public void updateUser(Long id, UserRequestDto userRequestDto) {
        var user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        repository.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserDto> getUsers() {
        var users = repository.findAll(0, 100, "id", "ASC", null); // Example: first 100 users
        return mapper.toDtoList(users);
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow());
    }
}
