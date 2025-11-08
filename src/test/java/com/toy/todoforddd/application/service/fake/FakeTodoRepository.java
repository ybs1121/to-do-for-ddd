package com.toy.todoforddd.application.service.fake;

import com.toy.todoforddd.domain.model.Todo;
import com.toy.todoforddd.domain.repository.TodoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeTodoRepository implements TodoRepository {

    private final List<Todo> todos = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Todo save(Todo todo) {
        todo.setId(counter.incrementAndGet());
        todos.add(todo);
        return todo;
    }

    @Override
    public Todo update(Todo todo) {
        Todo findTodo = todos.stream().filter(t -> t.getId().equals(todo.getId())).findFirst().orElseThrow();
        return Todo.builder()
                .id(findTodo.getId())
                .content(todo.getContent())
                .authorId(findTodo.getAuthorId())
                .completed(todo.getCompleted())
                .createdAt(findTodo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();

    }

    @Override
    public void delete(Long todoId) {
        todos.removeIf(t -> t.getId().equals(todoId));

    }

    @Override
    public boolean existsByAuthorIdAndTodoId(Long authorId, Long todoId) {
        return todos.stream().anyMatch(t -> t.getId().equals(todoId) && t.getAuthorId().equals(authorId));

    }

    @Override
    public Todo findByAuthorIdAndTodoId(Long authorId, Long todoId) {
        return todos.stream().filter(t -> t.getId().equals(todoId) && t.getAuthorId().equals(authorId)).findFirst().orElseThrow();
    }

    @Override
    public List<Todo> findAllIncompleteTodos() {
        return todos.stream().filter(t -> !t.getCompleted()).collect(Collectors.toList());
    }

    public void clear() {
        counter.set(0);
        todos.clear();
    }
}
