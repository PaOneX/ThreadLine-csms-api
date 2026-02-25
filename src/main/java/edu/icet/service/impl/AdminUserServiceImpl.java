package edu.icet.service.impl;

import edu.icet.exception.UserNotFoundException;
import edu.icet.mapper.RoleMapper;
import edu.icet.mapper.UserMapper;
import edu.icet.model.dto.Page;
import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.dto.user.UpdateUserRolesRequest;
import edu.icet.model.dto.user.UserDto;
import edu.icet.model.entity.Role;
import edu.icet.model.entity.User;
import edu.icet.repository.RoleRepository;
import edu.icet.repository.UserRepository;
import edu.icet.service.AdminUserService;
import edu.icet.util.pagination.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
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
        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        // Ensure all roles exist in the database
        Set<Role> roles = new java.util.HashSet<>();
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(null, roleName)));
            roles.add(role);
        }
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setEnabled(true);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto disableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setEnabled(false);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public Page<UserDto> getUsers(PageRequest pageRequest, String search) {
        int offset = (pageRequest.getPage() - 1) * pageRequest.getSize();
        List<User> users = userRepository.findAll(offset, pageRequest.getSize(), pageRequest.getSortBy(), pageRequest.getDirection(), search);
        long total = userRepository.count(search);
        int totalPages = (int) Math.ceil((double) total / pageRequest.getSize());
        List<UserDto> userDtos = users.stream().map(userMapper::toDto).toList();
        return new edu.icet.model.dto.Page<>(userDtos, totalPages, total, pageRequest.getSize(), pageRequest.getPage());
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean existsByUsernameIgnoreCase(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
