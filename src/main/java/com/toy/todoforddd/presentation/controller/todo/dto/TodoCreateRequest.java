package com.toy.todoforddd.presentation.controller.todo.dto;

import lombok.Data;

@Data
public class TodoCreateRequest {
    private Long authorId;
    private String title;
    private String content;
    private String pw;
}
