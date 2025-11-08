package com.toy.todoforddd.presentation.controller.todo.dto;

import lombok.Data;

@Data
public class TodoDeleteRequest {
    private Long todoId;
    private Long authorId;
}
