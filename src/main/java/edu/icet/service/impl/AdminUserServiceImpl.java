package edu.icet.service.impl;

import edu.icet.exception.UserNotFoundException;
import edu.icet.mapper.RoleMapper;
import edu.icet.mapper.UserMapper;
import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.dto.user.UpdateUserRolesRequest;
import edu.icet.model.dto.user.UserDto;
import edu.icet.model.entity.Role;
import edu.icet.model.entity.User;
import edu.icet.repository.RoleRepository;
import edu.icet.repository.UserRepository;
import edu.icet.service.AdminUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(AdminCreateUserRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);

        Set<Role> roles = roleMapper.toEntitySet(request.getRoles());
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUserRoles(Long userId, UpdateUserRolesRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Set<Role> roles = roleMapper.toEntitySet(request.getRoles());
        user.setRoles(roles);

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto enableUser(Long userId) {
        return null;
    }

    @Override
    public UserDto disableUser(Long userId) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return List.of();
    }

    @Override
    public UserDto getUserById(Long userId) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }
}
