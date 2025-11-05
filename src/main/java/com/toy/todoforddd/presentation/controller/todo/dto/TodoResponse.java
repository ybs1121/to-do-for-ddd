package com.toy.todoforddd.presentation.controller.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class TodoResponse {
    private Long authorId;
    private String title;
    private String content;
//    private String pw;


}
