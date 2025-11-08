package com.toy.todoforddd.domain.repository;

import com.toy.todoforddd.domain.model.Todo;

import java.util.List;

public interface TodoRepository {

    Todo save(Todo todo);

    Todo update(Todo todo);

    void delete(Long todoId);

    boolean existsByAuthorIdAndTodoId(Long authorId, Long todoId);

    Todo findByAuthorIdAndTodoId(Long authorId, Long todoId);

    List<Todo> findAllIncompleteTodos();

}
