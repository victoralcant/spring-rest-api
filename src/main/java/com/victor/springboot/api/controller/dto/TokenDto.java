package com.victor.springboot.api.controller.dto;

import lombok.Getter;

@Getter
public class TokenDto {

    private String token;
    private String bearer;

    public TokenDto(String token, String bearer) {
        this.token = token;
        this.bearer = bearer;
    }
}
