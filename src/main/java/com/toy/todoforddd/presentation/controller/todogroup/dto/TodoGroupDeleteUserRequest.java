package com.toy.todoforddd.presentation.controller.todogroup.dto;

import lombok.Data;

@Data
public class TodoGroupDeleteUserRequest {
    private Long todoGroupId;
    private Long userId;
}
