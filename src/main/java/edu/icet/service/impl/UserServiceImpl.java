package edu.icet.service.impl;

import edu.icet.mapper.UserMapper;
import edu.icet.model.dto.UserDTO;
import edu.icet.model.dto.UserRequestDto;
import edu.icet.model.entity.UserEntitty;
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
        UserEntitty userEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setPassword(userRequestDto.getPassword());
        repository.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserEntitty> userEntity = repository.findAll();
        return mapper.toDtoList(userEntity);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return mapper.toDto(repository.findById(id).get());
    }
}
