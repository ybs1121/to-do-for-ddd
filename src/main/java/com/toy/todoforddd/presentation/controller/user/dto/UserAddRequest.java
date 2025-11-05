package com.toy.todoforddd.presentation.controller.user.dto;

import lombok.Data;

@Data
public class UserAddRequest {
    private String userId ;
    private String pw;
    private String email;
}
