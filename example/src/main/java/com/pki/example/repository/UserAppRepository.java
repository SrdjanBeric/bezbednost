package com.pki.example.repository;

import com.pki.example.models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    public UserApp findByUsername(String username);

    @Query("select u from UserApp u where u.role.name=:roleName")
    public List<UserApp> findAllByRole(@Param("roleName") String roleName);
}
