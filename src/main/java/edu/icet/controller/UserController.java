package edu.icet.controller;

import edu.icet.model.dto.UserDTO;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/user")
    public List<UserDTO> getUsers(){
        return  service.getUsers();
    }

    @GetMapping("/user/{id}")
    public  UserDTO getUser(@PathVariable Integer id){
        return service.getUserById(id);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserDTO userDTO){
        service.addUser(userDTO);
    }

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody UserDTO userDTO){
        service.updateUser(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id){
        service.deleteUser(id);
    }
}
