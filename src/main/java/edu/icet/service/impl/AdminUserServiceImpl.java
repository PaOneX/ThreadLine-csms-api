package edu.icet.service.impl;

import edu.icet.exception.UserNotFoundException;
import edu.icet.mapper.RoleMapper;
import edu.icet.mapper.UserMapper;
import edu.icet.model.dto.user.AdminCreateUserRequest;
import edu.icet.model.dto.user.UpdateUserRolesRequest;
import edu.icet.model.dto.user.UserDto;
import edu.icet.model.dto.user.UserSearchCriteria;
import edu.icet.model.entity.Role;
import edu.icet.model.entity.User;
import edu.icet.repository.RoleRepository;
import edu.icet.repository.UserRepository;
import edu.icet.service.AdminUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        Set<Role> roles = roleMapper.toEntitySet(request.getRoles());
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
    public Page<UserDto> findUsers(UserSearchCriteria criteria, Pageable pageable) {
        Page<User> userPage;

        if (criteria.getRole() != null && !criteria.getRole().isEmpty()) {
            userPage = userRepository.findByRoleAndEnabled(
                    criteria.getRole(),
                    criteria.getEnabled(),
                    pageable
            );
        } else {
            userPage = userRepository.findUsersByCriteria(
                    criteria.getUsername(),
                    criteria.getEmail(),
                    criteria.getEnabled(),
                    pageable
            );
        }

        // Use Page.map() to convert Page<User> to Page<UserDto>
        return userPage.map(userMapper::toDto);
    }


    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }
}
