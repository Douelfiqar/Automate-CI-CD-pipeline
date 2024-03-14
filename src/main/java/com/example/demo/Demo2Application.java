package com.example.demo;

import com.example.demo.entities.Users;
import com.example.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args -> {
            userRepository.save(Users.builder().username("idriss").passwd("azer").build());
            userRepository.save(Users.builder().username("doo").passwd("1234").build());
            userRepository.save(Users.builder().username("FC04").passwd("@]*$").build());
        };
    }
}
