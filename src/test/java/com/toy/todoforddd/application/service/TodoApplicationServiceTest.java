package com.toy.todoforddd.application.service;

import com.toy.todoforddd.application.service.fake.FakeTodoRepository;
import com.toy.todoforddd.application.service.fake.FakeUserRepository;
import com.toy.todoforddd.common.exception.TodoException;
import com.toy.todoforddd.domain.model.User;
import com.toy.todoforddd.domain.repository.TodoRepository;
import com.toy.todoforddd.domain.repository.UserRepository;
import com.toy.todoforddd.presentation.controller.todo.dto.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class TodoApplicationServiceTest {

    private final FakeTodoRepository fakeTodoRepository = new FakeTodoRepository();
    private final FakeUserRepository fakeUserRepository = new FakeUserRepository();

    private final TodoApplicationService todoApplicationService = new TodoApplicationService(fakeUserRepository, fakeTodoRepository);

    @BeforeEach
    void setUp() {
        fakeTodoRepository.clear();
        fakeUserRepository.clear();
    }


    @Test
    void TodoCreateRequest로_저장할_수_있다() {
        // Given
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setContent("test");
        todoCreateRequest.setAuthorId(1L);

        fakeUserRepository.save(User.builder()
                .userId("123")
                .build());


        // When
        TodoResponse todo = todoApplicationService.createTodo(todoCreateRequest);
        // Then
        assertThat(todo.getAuthorId()).isEqualTo(todoCreateRequest.getAuthorId());
        assertThat(todo.getContent()).isEqualTo(todoCreateRequest.getContent());

    }

    @Test
    void TodoUpdateRequest로_업데이트_할_수_있다() {
        // Given
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setContent("test");
        todoCreateRequest.setAuthorId(1L);

        User user = fakeUserRepository.save(User.builder()
                .userId("tester")
                .build());

        TodoResponse todo1 = todoApplicationService.createTodo(todoCreateRequest);

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest();
        todoUpdateRequest.setContent("test22");
        todoUpdateRequest.setAuthorId(user.getId());
        todoUpdateRequest.setTodoId(todo1.getId());


        // When
        TodoResponse todo = todoApplicationService.updateTodo(todoUpdateRequest);
        // Then
        assertThat(todo.getAuthorId()).isEqualTo(todoUpdateRequest.getAuthorId());
        assertThat(todo.getContent()).isEqualTo(todoUpdateRequest.getContent());
    }

    @Test
    void TodoDeleteRequest로_삭제할_수_있다() {
        // Given
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setContent("test");
        todoCreateRequest.setAuthorId(1L);

        User user = fakeUserRepository.save(User.builder()
                .userId("tester")
                .build());

        TodoResponse saveTodo = todoApplicationService.createTodo(todoCreateRequest);

        TodoDeleteRequest todoDeleteRequest = new TodoDeleteRequest();
        todoDeleteRequest.setTodoId(saveTodo.getId());
        todoDeleteRequest.setAuthorId(saveTodo.getAuthorId());

        // When
        todoApplicationService.deleteTodo(todoDeleteRequest);
    }


    @Test
    void TodoDeleteRequest로_찾지못하면_에러를_반환한다() {
        // Given
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setContent("test");
        todoCreateRequest.setAuthorId(1L);

        User user = fakeUserRepository.save(User.builder()
                .userId("tester")
                .build());

        TodoResponse saveTodo = todoApplicationService.createTodo(todoCreateRequest);

        TodoDeleteRequest todoDeleteRequest = new TodoDeleteRequest();
        todoDeleteRequest.setTodoId(null);
        todoDeleteRequest.setAuthorId(saveTodo.getAuthorId());

        // When & Then
        assertThatThrownBy(() -> todoApplicationService.deleteTodo(todoDeleteRequest))
                .isInstanceOf(TodoException.class);
    }


    @Test
    void TodoCompletedRequest로_상태를_변경할_수있다() {
        // Given
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setContent("test");
        todoCreateRequest.setAuthorId(1L);

        User user = fakeUserRepository.save(User.builder()
                .userId("tester")
                .build());

        TodoResponse saveTodo = todoApplicationService.createTodo(todoCreateRequest);
        TodoCompletedRequest todoCompletedRequest = new TodoCompletedRequest();
        todoCompletedRequest.setTodoId(saveTodo.getId());
        todoCompletedRequest.setAuthorId(saveTodo.getId());
        todoCompletedRequest.setCompleted(true);

        // When
        TodoResponse todoResponse = todoApplicationService.updateCompleted(todoCompletedRequest);
        // Then
        assertThat(todoResponse.getCompleted()).isEqualTo(todoCompletedRequest.getCompleted());


    }

    @Test
    void TodoCompletedRequest로_못찾으면_에러를_반환한다() {
        // Given
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setContent("test");
        todoCreateRequest.setAuthorId(1L);

        User user = fakeUserRepository.save(User.builder()
                .userId("tester")
                .build());

        TodoResponse saveTodo = todoApplicationService.createTodo(todoCreateRequest);
        TodoCompletedRequest todoCompletedRequest = new TodoCompletedRequest();
        todoCompletedRequest.setTodoId(null);
        todoCompletedRequest.setAuthorId(saveTodo.getId());
        todoCompletedRequest.setCompleted(true);

        // When & Then
        assertThatThrownBy(() -> todoApplicationService.updateCompleted(todoCompletedRequest))
                .isInstanceOf(TodoException.class);


    }

}