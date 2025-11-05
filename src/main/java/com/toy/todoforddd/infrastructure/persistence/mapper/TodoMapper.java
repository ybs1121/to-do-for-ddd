package com.toy.todoforddd.infrastructure.persistence.mapper;

import com.toy.todoforddd.domain.model.Todo;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoMapper {
    /**
     * Domain Model (Post) → DB Entity (PostEntity) 변환
     */
    public TodoEntity toEntity(Todo todo) {
        if (todo == null) {
            return null;
        }

        return TodoEntity.builder()
                .id(todo.getId())
                .authorId(todo.getAuthorId())
                .title(todo.getTitle())
                .content(todo.getContent())
                .pw(todo.getPw())
                .completed(todo.getCompleted())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }

    /**
     * DB Entity (PostEntity) → Domain Model (Post) 변환
     */
    public Todo toDomain(TodoEntity todoEntity) {
        if (todoEntity == null) {
            return null;
        }

        return Todo.builder()
                .id(todoEntity.getId())
                .authorId(todoEntity.getAuthorId())
                .title(todoEntity.getTitle())
                .content(todoEntity.getContent())
                .pw(todoEntity.getPw())
                .completed(todoEntity.getCompleted())
                .createdAt(todoEntity.getCreatedAt())
                .updatedAt(todoEntity.getUpdatedAt())
                .build();
    }

    /**
     * Domain List → Entity List 변환
     */
    public List<TodoEntity> toEntityList(List<Todo> todos) {
        if (todos == null) {
            return null;
        }

        return todos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Entity List → Domain List 변환
     */
    public List<Todo> toDomainList(List<TodoEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());

    }
}
