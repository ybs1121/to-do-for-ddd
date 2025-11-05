package com.toy.todoforddd.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Todo {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private String pw;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
