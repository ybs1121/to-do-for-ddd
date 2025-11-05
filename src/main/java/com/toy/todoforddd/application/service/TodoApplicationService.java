package com.toy.todoforddd.application.service;

import com.toy.todoforddd.domain.model.Todo;
import com.toy.todoforddd.domain.model.User;
import com.toy.todoforddd.domain.repository.TodoRepository;
import com.toy.todoforddd.domain.repository.UserRepository;
import com.toy.todoforddd.presentation.controller.todo.dto.TodoCreateRequest;
import com.toy.todoforddd.presentation.controller.todo.dto.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TodoApplicationService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;


    public TodoResponse createTodo(TodoCreateRequest todoCreateRequest) {
        User user = userRepository.getById(todoCreateRequest.getAuthorId());
        Todo todo = user.createPost(todoCreateRequest.getTitle(), todoCreateRequest.getContent(),
                todoCreateRequest.getPw(), LocalDateTime.now());
        Todo saveTodo = todoRepository.save(todo);
        return TodoResponse.of(saveTodo.getAuthorId(), saveTodo.getTitle(), saveTodo.getContent());
    }
}

