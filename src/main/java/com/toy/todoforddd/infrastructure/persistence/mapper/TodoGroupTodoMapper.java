package com.toy.todoforddd.infrastructure.persistence.mapper;

import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.TodoId;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupTodoEntity;
import org.springframework.stereotype.Component;

@Component
public class TodoGroupTodoMapper {

    public TodoGroupTodoEntity toEntity(TodoId todoId, TodoGroupId todoGroupId) {
        if (todoGroupId == null || todoId == null) {
            return null;
        }
        return TodoGroupTodoEntity.builder()
                .todoGroupId(todoGroupId.id())
                .id(todoId.id())
                .build();
    }

    public TodoId toDomain(TodoGroupTodoEntity todoGroupTodoEntity) {
        return new TodoId(todoGroupTodoEntity.getId());
    }

}
