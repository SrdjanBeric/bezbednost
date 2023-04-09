package com.pki.example.controller;

import com.pki.example.dto.CertificateDto;
import com.pki.example.models.UserApp;
import com.pki.example.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserAppService userAppService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserApp> getAllUsers(){
        return userAppService.findAll();
    }

    @GetMapping("/availableSubjectUsers")
    public List<UserApp> getAvailableSubjectUsers(){
        UserApp loggedInUser = userAppService.FindByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return userAppService.getAvailableSubjectUsers(loggedInUser);
    }
}
