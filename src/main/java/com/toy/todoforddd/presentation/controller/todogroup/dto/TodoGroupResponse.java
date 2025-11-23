package com.toy.todoforddd.presentation.controller.todogroup.dto;

import com.toy.todoforddd.domain.model.TodoGroup;
import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.TodoId;
import com.toy.todoforddd.domain.model.key.UserId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TodoGroupResponse {

    private TodoGroupId todoGroupId; // TodoGroup 아이디
    private List<Long> userIds;
    private List<Long> todoIds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createUserId;


    public static TodoGroupResponse toTodoGroupResponse(TodoGroup todoGroup) {
        TodoGroupResponse todoGroupResponse = new TodoGroupResponse();
        todoGroupResponse.setTodoGroupId(todoGroup.getTodoGroupId());
        todoGroupResponse.setTodoIds(todoGroup.getTodoIds().stream().map(TodoId::id).toList());
        todoGroupResponse.setUserIds(todoGroup.getUserIds().stream().map(UserId::id).toList());
        todoGroupResponse.setCreatedAt(todoGroup.getCreatedAt());
        todoGroupResponse.setUpdatedAt(todoGroup.getUpdatedAt());
        todoGroupResponse.setCreateUserId(todoGroup.getCreateUserId().id());
        return todoGroupResponse;
    }
}
