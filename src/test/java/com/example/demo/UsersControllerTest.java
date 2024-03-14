package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.entities.Users;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void createUser() throws Exception {
        Users user = Users.builder().id(1).username("test").passwd("password").build();
        when(userRepository.save(any(Users.class))).thenReturn(user);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"test\",\"passwd\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.passwd").value("password"));
    }

    @Test
    void getAllUsers() throws Exception {
        Users user1 = Users.builder().id(1).username("user1").passwd("pass1").build();
        Users user2 = Users.builder().id(2).username("user2").passwd("pass2").build();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].passwd").value("pass1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].passwd").value("pass2"));
    }

    @Test
    void getUserById() throws Exception {
        Users user = Users.builder().id(1).username("test").passwd("password").build();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.passwd").value("password"));
    }

    @Test
    void updateUser() throws Exception {
        Users user = Users.builder().id(1).username("test").passwd("password").build();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"updated\",\"passwd\":\"updatedPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("updated"))
                .andExpect(jsonPath("$.passwd").value("updatedPassword"));
    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userRepository).deleteById(anyInt());

        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk());
    }
}
