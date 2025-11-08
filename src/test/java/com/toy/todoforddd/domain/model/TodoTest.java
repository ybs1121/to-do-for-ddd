package com.toy.todoforddd.domain.model;


import com.toy.todoforddd.common.exception.TodoException;
import com.toy.todoforddd.presentation.controller.todo.dto.TodoCompletedRequest;
import com.toy.todoforddd.presentation.controller.todo.dto.TodoCreateRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TodoTest {


    @Test
    void ToDo를_생성할_수_있다() {
        //Given

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setAuthorId(1L);
        todoCreateRequest.setContent("생성완료");

        // When
        LocalDateTime now = LocalDateTime.now();
        Todo todo = Todo.create(todoCreateRequest.getAuthorId(), todoCreateRequest.getContent(), now);
        // Then
        assertThat(todo.getAuthorId()).isEqualTo(todoCreateRequest.getAuthorId());
        assertThat(todo.getContent()).isEqualTo(todoCreateRequest.getContent());
        assertThat(todo.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void ToDo를_생성할_때_내용이_없으면_예외가_발생한다() {
        //Given

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setAuthorId(1L);
        todoCreateRequest.setContent("");

        // When & Then
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> {
            Todo.create(todoCreateRequest.getAuthorId(), todoCreateRequest.getContent(), now);
        }).isInstanceOf(TodoException.class);


    }

    @Test
    void ToDo의_완료상태를_변경할_수_있다() {
        // Given
        Todo todo = Todo.builder()
                .id(1L)
                .authorId(1L)
                .completed(false)
                .build();
        // When
        LocalDateTime now = LocalDateTime.now();
        Todo updateTodo = todo.updateCompleted(true, now);
        // Then
        assertThat(updateTodo.getUpdatedAt()).isEqualTo(now);
        assertThat(updateTodo.getCompleted()).isEqualTo(true);

    }

    @Test
    void ToDo의_내용을변경할_수_있다() {
        // Given
        Todo todo = Todo.builder()
                .id(1L)
                .authorId(1L)
                .content("수정전")
                .completed(false)
                .build();
        // When
        LocalDateTime now = LocalDateTime.now();
        Todo updateTodo = todo.updateContent("수정후", now);
        // Then
        assertThat(updateTodo.getUpdatedAt()).isEqualTo(now);
        assertThat(updateTodo.getCompleted()).isEqualTo(false);
        assertThat(updateTodo.getAuthorId()).isEqualTo(1L);
        assertThat(updateTodo.getContent()).isEqualTo("수정후");

    }

    @Test
    void TODO를_업데이트할때_내용이_없으면_TodoException을_발생시킨다() {
        // Given
        Todo todo = Todo.builder()
                .id(1L)
                .authorId(1L)
                .content("123")
                .completed(false)
                .build();
        // When & Then
        LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() ->
                todo.updateContent("", now)
        ).isInstanceOf(TodoException.class);
    }

}