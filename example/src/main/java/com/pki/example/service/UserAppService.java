package com.pki.example.service;

import com.pki.example.models.UserApp;
import com.pki.example.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAppService implements UserDetailsService {
    @Autowired
    private UserAppRepository userAppRepository;

    public UserApp FindByUsername(String username){
        return userAppRepository.findByUsername(username);
    }

    public List<UserApp> findAll(){ return userAppRepository.findAll(); }

    public List<UserApp> getAvailableSubjectUsers(UserApp userApp){
        if(userApp.getRole().getName().equals("END_ENTITY")){
            return null;
        }else if(userApp.getRole().getName().equals("ADMIN")){
            return findAll();
        }else if(userApp.getRole().getName().equals("INTERMEDIARY")){
            List<UserApp> users = new ArrayList<>();
            users.addAll(userAppRepository.findAllByRole("INTERMEDIARY"));
            users.addAll(userAppRepository.findAllByRole("END_ENTITY"));
            return users;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAppRepository.findByUsername(username);
    }
}
