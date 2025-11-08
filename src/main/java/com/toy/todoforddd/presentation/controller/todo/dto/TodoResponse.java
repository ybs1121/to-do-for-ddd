package com.toy.todoforddd.presentation.controller.todo.dto;

import com.toy.todoforddd.domain.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class TodoResponse {
    private Long id;
    private Long authorId;
    private String content;
    private Boolean completed;

    public static TodoResponse of(Todo todo) {
        return TodoResponse.of(todo.getId(), todo.getAuthorId(), todo.getContent(), todo.getCompleted());
    }
}