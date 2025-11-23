package com.toy.todoforddd.presentation.controller.todogroup.dto;

import lombok.Data;

@Data
public class TodoGroupDeleteTodoRequest {
    private Long todoGroupId;
    private Long todoId;
}
