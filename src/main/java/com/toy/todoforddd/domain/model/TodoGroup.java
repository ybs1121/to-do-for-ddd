package com.toy.todoforddd.domain.model;

import com.toy.todoforddd.common.exception.TodoGroupException;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class TodoGroup {
    private Long todoGroupId; // TodoGroup 아이디
    private Set<Long> userIds;
    private Set<Long> todoIds;
    private Long createUserId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // TodoGroup을 생성할 수 있다.
    public static TodoGroup createTodoGroup(Long createUserId, LocalDateTime createdAt) {
        HashSet<Long> userIds = new HashSet<>();
        return TodoGroup.builder()
                .userIds(userIds)
                .createUserId(createUserId)
                .createdAt(createdAt)
                .build();
    }

    // Todo를 추가할 수 있다.
    public TodoGroup addTodo(Long todoId, LocalDateTime updatedAt) {
        if (todoId == null) {
            throw new TodoGroupException("TodoId는 비어있을 수 없습니다.");
        }

        if (todoIds == null || todoIds.isEmpty()) {
            todoIds = new HashSet<>();
        }

        this.todoIds.add(todoId);

        return TodoGroup.builder()
                .todoGroupId(this.todoGroupId)
                .createUserId(this.createUserId)
                .userIds(this.userIds)
                .todoIds(this.todoIds)
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    // Todo를 삭제할 수 있다.
    public TodoGroup removeTodo(Long todoId, LocalDateTime updatedAt) {
        if (todoId == null) {
            throw new TodoGroupException("TodoId는 비어있을 수 없습니다.");
        }

        if (todoIds == null || todoIds.isEmpty()) {
            throw new TodoGroupException("지울 수 있는 항목이 없습니다.");
        }


        this.todoIds.remove(todoId);

        return TodoGroup.builder()
                .todoGroupId(this.todoGroupId)
                .createUserId(this.createUserId)
                .userIds(this.userIds)
                .todoIds(this.todoIds)
                .build();
    }

    // userId를 추가할 수 있다.
    public TodoGroup addUserId(Long userId, LocalDateTime updatedAt) {
        if (userId == null) {
            throw new TodoGroupException("userId는 비어있을 수 없습니다.");
        }

        this.userIds.add(userId);

        return TodoGroup.builder()
                .todoGroupId(this.todoGroupId)
                .createUserId(this.createUserId)
                .userIds(this.userIds)
                .todoIds(this.todoIds)
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    // userId를 삭제할 수 있다.
    public TodoGroup removeUserId(Long userId, LocalDateTime updatedAt) {
        if (userId == null) {
            throw new TodoGroupException("userId는 비어있을 수 없습니다.");
        }

        if (userId.equals(this.createUserId)) {
            throw new TodoGroupException("생성자를 추방 할 수 없습니다.");
        }

        this.userIds.remove(userId);

        return TodoGroup.builder()
                .todoGroupId(this.todoGroupId)
                .createUserId(this.createUserId)
                .userIds(this.userIds)
                .todoIds(this.todoIds)
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
