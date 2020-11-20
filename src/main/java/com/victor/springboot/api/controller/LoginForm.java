package com.victor.springboot.api.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class LoginForm {

    private String email;
    private String senha;

    public UsernamePasswordAuthenticationToken convertToAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
