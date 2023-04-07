package com.pki.example.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDao {
//    private final List<UserDetails> APPLICATION_USERS = new ArrayList<>();
//
//    public UserDao() {
//        // inicijalno dodavanje korisnika u listu
//        addUser("admin.mail@gmail.com", "password1", "ROLE_ADMIN");
//        addUser("user.mail@gmail.com", "password", "ROLE_USER");
//    }
//
//    public void addUser(String username, String password, String role) {
//        UserDetails newUser = new User(
//                username,
//                password,
//                Collections.singleton(new SimpleGrantedAuthority(role))
//        );
//        APPLICATION_USERS.add(newUser);
//
//
//
//    }
//
//    public UserDetails findUserByEmail(String email) {
//
//        return APPLICATION_USERS.stream()
//                .filter(u -> u.getUsername().equals(email))
//                .findFirst()
//                .orElseThrow(() -> new UsernameNotFoundException("No user was found"));
//    }
//
//}





    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(



                //ovde dodajemo nove usere
                new User(
                        "admin.mail@gmail.com",
                        "password1",
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))

                ),

                new User(
                        "user.mail@gmail.com",
                        "password",
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
                )


                //ovde dodajemo novog usera

        );





    public UserDetails findUserByEmail(String email) {


        System.out.println(
                APPLICATION_USERS.size()
        );

            return APPLICATION_USERS
                    .stream()
                    .filter(u -> u.getUsername().equals(email))
                    .findFirst()
                    .orElseThrow(() -> new UsernameNotFoundException("No user was found"))
                    ;


    }
}




