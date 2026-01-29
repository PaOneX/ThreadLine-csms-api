package edu.icet.controller;

import edu.icet.model.dto.UserDTO;
import edu.icet.model.dto.UserRequestDto;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public List<UserDTO> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping
    public void addUser(@RequestBody UserRequestDto userRequestDto) {
        service.addUser(userRequestDto);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        service.updateUser(id,userRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
