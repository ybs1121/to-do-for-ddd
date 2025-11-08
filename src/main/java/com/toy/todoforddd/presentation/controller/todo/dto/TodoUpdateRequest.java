package com.toy.todoforddd.presentation.controller.todo.dto;

import lombok.Data;

@Data
public class TodoUpdateRequest {
    private Long todoId;
    private Long authorId;
    private String content;
}
