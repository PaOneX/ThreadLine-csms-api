package edu.icet.service.impl;

import edu.icet.mapper.UserMapper;
import edu.icet.model.dto.user.UserDto;
import edu.icet.model.dto.user.UserRequestDto;
import edu.icet.model.entity.User;
import edu.icet.repository.UserRepository;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public void addUser(UserRequestDto requestDto) {
        repository.save(mapper.toEntity(requestDto));
    }

    @Override
    public void updateUser(Long id, UserRequestDto userRequestDto) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> user = repository.findAll();
        return mapper.toDtoList(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDto(user);
    }
}
