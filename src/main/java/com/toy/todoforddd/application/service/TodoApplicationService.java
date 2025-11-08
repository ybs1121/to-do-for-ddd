package com.toy.todoforddd.application.service;

import com.toy.todoforddd.common.exception.TodoException;
import com.toy.todoforddd.domain.model.Todo;
import com.toy.todoforddd.domain.model.User;
import com.toy.todoforddd.domain.repository.TodoRepository;
import com.toy.todoforddd.domain.repository.UserRepository;
import com.toy.todoforddd.infrastructure.persistence.mapper.TodoMapper;
import com.toy.todoforddd.presentation.controller.todo.dto.*;
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

        Todo todo = Todo.create(
                todoCreateRequest.getAuthorId(),
                todoCreateRequest.getContent(),
                LocalDateTime.now()
        );

        Todo saveTodo = todoRepository.save(todo);
        return TodoResponse.of(saveTodo.getId(), saveTodo.getAuthorId(), saveTodo.getContent(), saveTodo.getCompleted());
    }

    public TodoResponse updateTodo(TodoUpdateRequest todoUpdateRequest) {
        User user = userRepository.getById(todoUpdateRequest.getAuthorId());

        Todo todo = todoRepository.findByAuthorIdAndTodoId(user.getId(), todoUpdateRequest.getTodoId());
        Todo updateTodo = todo.updateContent(todoUpdateRequest.getContent(), LocalDateTime.now());
        Todo saveTodo = todoRepository.update(updateTodo);
        return TodoResponse.of(saveTodo.getId(), saveTodo.getAuthorId(), saveTodo.getContent(), saveTodo.getCompleted());
    }

    public void deleteTodo(TodoDeleteRequest todoDeleteRequest) {
        // 검증
        if (!todoRepository.existsByAuthorIdAndTodoId(todoDeleteRequest.getAuthorId(), todoDeleteRequest.getTodoId())) {
            throw new TodoException("존재하지 않는 Todo 입니다.");
        }

        todoRepository.delete(todoDeleteRequest.getTodoId());
    }

    public TodoResponse updateCompleted(TodoCompletedRequest todoCompletedRequest) {
        if (!todoRepository.existsByAuthorIdAndTodoId(todoCompletedRequest.getAuthorId(), todoCompletedRequest.getTodoId())) {
            throw new TodoException("존재하지 않는 Todo 입니다.");
        }


        Todo todo = todoRepository.findByAuthorIdAndTodoId(todoCompletedRequest.getAuthorId(), todoCompletedRequest.getTodoId());
        Todo updateTodo = todo.updateCompleted(todoCompletedRequest.getCompleted(), LocalDateTime.now());
        Todo saveTodo = todoRepository.update(updateTodo);

        return TodoResponse.of(saveTodo);

    }

}

