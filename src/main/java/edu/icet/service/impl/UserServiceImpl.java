package edu.icet.service.impl;

import edu.icet.mapper.UserMapper;
import edu.icet.model.dto.UserDTO;
import edu.icet.model.entity.UserEntitty;
import edu.icet.repository.UserRepository;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    @Override
    public void addUser(UserDTO userDTO) {
       repository.save( mapper.toEntity(userDTO));
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        repository.save(mapper.toEntity(userDTO));
    }

    @Override
    public void deleteUser(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserDTO> getUsers() {
       List<UserEntitty> userEntity = repository.findAll();
       return mapper.toDtoList(userEntity);
    }

    @Override
    public UserDTO getUserById(Integer id) {

        return  mapper.toDto(repository.findById(id).get());
    }
}
