package com.toy.todoforddd.presentation.controller.todogroup.dto;

import lombok.Data;

@Data
public class TodoGroupAddUserRequest {
    private Long todoGroupId;
    private Long userId;
}
