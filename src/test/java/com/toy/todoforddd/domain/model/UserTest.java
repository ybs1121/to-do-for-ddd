package com.toy.todoforddd.domain.model;


import com.toy.todoforddd.presentation.controller.todo.dto.TodoCreateRequest;
import com.toy.todoforddd.presentation.controller.user.dto.UserAddRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

    @Test
    void userAddRequest는_User_도메인을_생성_할_수_있다() {
        // Given
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserId("tester");
        userAddRequest.setPw("tester");
        userAddRequest.setEmail("tester@test.com");

        // When
        User user = User.create(userAddRequest);
        // Then
        assertThat(user.getUserId()).isEqualTo(userAddRequest.getUserId());
        assertThat(user.getPw()).isEqualTo(userAddRequest.getPw());
        assertThat(user.getEmail()).isEqualTo(userAddRequest.getEmail());

    }

    @Test
    void User는_Todo를_생성할_수_있다() {
        // Given
        User tester = User.builder()
                .id(1L)
                .userId("tester")
                .build();

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setContent("test");
        todoCreateRequest.setAuthorId(tester.getId());
        LocalDateTime createdAt = LocalDateTime.now();
        // When

        Todo todo = tester.createTodo(todoCreateRequest.getContent(), createdAt);

        // Then
        assertThat(todo.getAuthorId()).isEqualTo(todoCreateRequest.getAuthorId());
        assertThat(todo.getContent()).isEqualTo(todoCreateRequest.getContent());
        assertThat(todo.getCreatedAt()).isEqualTo(createdAt);
        assertThat(todo.getCompleted()).isFalse();

    }
}