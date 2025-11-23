package com.toy.todoforddd.presentation.controller.todogroup.dto;

import lombok.Data;

@Data
public class TodoGroupAddTodoRequest {
    private Long todoGroupId;
    private Long authorId;
    private String content;
}
