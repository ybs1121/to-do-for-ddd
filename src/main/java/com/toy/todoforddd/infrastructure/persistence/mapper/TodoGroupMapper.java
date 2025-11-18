package com.toy.todoforddd.infrastructure.persistence.mapper;

import com.toy.todoforddd.domain.model.TodoGroup;
import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.UserId;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupEntity;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupTodoEntity;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TodoGroupMapper {

    private final TodoGroupUserMapper todoGroupUserMapper;
    private final TodoGroupTodoMapper todoGroupTodoMapper;

    public TodoGroupEntity toEntity(TodoGroup todoGroup) {
        return TodoGroupEntity.builder()
                .todoGroupId(todoGroup.getTodoGroupId().id())
                .todos(todoGroup.getTodoIds().stream().map(todoId -> todoGroupTodoMapper.toEntity(todoId, todoGroup.getTodoGroupId())).collect(Collectors.toList()))
                .createUserId(todoGroup.getCreateUserId().id())
                .users(todoGroup.getUserIds().stream().map(userId -> todoGroupUserMapper.toEntity(userId, todoGroup.getTodoGroupId())).collect(Collectors.toList()))
                .createdAt(todoGroup.getCreatedAt())
                .build();
    }

    public TodoGroup toDomain(TodoGroupEntity todoGroupEntity, List<TodoGroupUserEntity> todoGroupUserEntities, List<TodoGroupTodoEntity> todoGroupTodoEntities) {
        return TodoGroup.builder()
                .todoGroupId(new TodoGroupId(todoGroupEntity.getTodoGroupId()))
                .createUserId(new UserId(todoGroupEntity.getCreateUserId()))
                .todoIds(todoGroupTodoEntities.stream().map(todoGroupTodoMapper::toDomain).collect(Collectors.toList()))
                .userIds(todoGroupUserEntities.stream().map(todoGroupUserMapper::toDomain).collect(Collectors.toList()))
                .createdAt(todoGroupEntity.getCreatedAt())
                .updatedAt(todoGroupEntity.getUpdatedAt())
                .build();
    }
}
