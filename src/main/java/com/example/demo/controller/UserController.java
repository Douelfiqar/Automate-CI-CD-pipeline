package com.example.demo.controller;

import com.example.demo.entities.Users;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Create
    @PostMapping("/")
    public Users createUser(@RequestBody Users user) {
        return userRepository.save(user);
    }


    @GetMapping("/test")
    public String createUser() {
        return "test";
    }

    // Read All
    @GetMapping("/")
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/test")
    public String testMessage() {
        return "Test message";
    }

    // Read One
    @GetMapping("/{id}")
    public Optional<Users> getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    // Update
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable int id, @RequestBody Users newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPasswd(newUser.getPasswd());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}
