package com.toy.todoforddd.infrastructure.persistence.mapper;

import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.TodoId;
import com.toy.todoforddd.domain.model.key.UserId;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupUserEntity;
import org.springframework.stereotype.Component;

@Component
public class TodoGroupUserMapper {


    public TodoGroupUserEntity toEntity(UserId userId, TodoGroupId todoGroupId) {
        if (todoGroupId == null || userId == null) {
            return null;
        }

        return TodoGroupUserEntity.builder()
                .todoGroupId(todoGroupId.id())
                .id(userId.id())
                .build();
    }

    public UserId toDomain(TodoGroupUserEntity todoGroupUserEntity) {
        return new UserId(todoGroupUserEntity.getId());
    }


}
