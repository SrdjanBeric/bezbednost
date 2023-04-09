package com.pki.example.service;

import com.pki.example.model.UserApp;
import com.pki.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createNewUser(UserApp user) {
        userRepository.save(user);
    }
}
