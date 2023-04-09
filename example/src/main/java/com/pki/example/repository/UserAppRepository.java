package com.pki.example.repository;

import com.pki.example.models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    public UserApp findByUsername(String username);
}
