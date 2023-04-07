package com.pki.example.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;



}
