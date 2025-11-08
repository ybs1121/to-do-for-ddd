package com.toy.todoforddd.domain.model;

import com.toy.todoforddd.common.exception.TodoException;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Todo {
    private Long id;
    private Long authorId;
    private String content;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static Todo create(Long authorId, String content, LocalDateTime now) {
        if (content == null || content.isBlank()) {
            throw new TodoException("내용이 비어있습니다");
        }

        return Todo.builder()
                .authorId(authorId)
                .content(content)
                .completed(false)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public Todo updateContent(String content, LocalDateTime updatedAt) {
        if (content == null || content.isBlank()) {
            throw new TodoException("내용이 비어있습니다");
        }

        return Todo.builder()
                .id(this.id)
                .authorId(this.authorId)
                .content(content)
                .completed(this.completed)
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    //Todo는 완료 여부를 바꿀 수 있다.
    public Todo updateCompleted(Boolean completed, LocalDateTime updatedAt) {
        return Todo.builder()
                .id(this.id)
                .authorId(this.authorId)
                .content(this.content)
                .completed(completed)  // ← 새로운 값
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)  // ← 새로운 값
                .build();
    }
}
