package com.toy.todoforddd.presentation.controller.todo.dto;

import lombok.Data;

@Data
public class TodoCompletedRequest {
    private Long todoId;
    private Long authorId;
    private Boolean completed;
}
