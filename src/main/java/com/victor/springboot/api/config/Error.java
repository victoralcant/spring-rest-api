package com.victor.springboot.api.config;

import lombok.Getter;

@Getter
public class Error {

    private String field;
    private String message;

    public Error(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
