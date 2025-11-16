package com.toy.todoforddd.domain.model;

import com.toy.todoforddd.common.exception.TodoGroupException;
import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.TodoId;
import com.toy.todoforddd.domain.model.key.UserId;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class TodoGroup {
    private TodoGroupId todoGroupId; // TodoGroup 아이디
    private Set<UserId> userIds;
    private Set<TodoId> todoIds;
    private UserId createUserId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // TodoGroup을 생성할 수 있다.
    public static TodoGroup createTodoGroup(UserId createUserId, LocalDateTime createdAt) {
        if (createUserId == null) {
            throw new TodoGroupException("createUserId 있을 수 없습니다");
        }
        if (createdAt == null) {
            throw new TodoGroupException("생성시간이 없습니다.");
        }
        HashSet<UserId> userIds = new HashSet<>();
        userIds.add(createUserId);
        return TodoGroup.builder()
                .todoGroupId(null) // DB 생성
                .userIds(userIds)
                .createUserId(createUserId)
                .todoIds(new HashSet<>())
                .userIds(userIds)
                .createdAt(createdAt)
                .build();
    }

    // Todo를 추가할 수 있다.
    public TodoGroup addTodo(TodoId todoId, LocalDateTime updatedAt) {

        if (todoId == null) {
            throw new TodoGroupException("TodoId는 비어있을 수 없습니다");
        }

        // 이미 존재하는지 확인
        if (this.todoIds.contains(todoId)) {
            throw new TodoGroupException("이미 그룹에 추가된 Todo입니다");
        }


        // 새로운 Set 생성 (기존 Set은 불변)
        Set<TodoId> newTodoIds = new HashSet<>(this.todoIds);
        newTodoIds.add(todoId);


        return TodoGroup.builder()
                .todoGroupId(this.todoGroupId)
                .createUserId(this.createUserId)
                .userIds(this.userIds)
                .todoIds(newTodoIds)
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    // Todo를 삭제할 수 있다.
    public TodoGroup removeTodo(TodoId todoId, LocalDateTime updatedAt) {
        if (todoId == null) {
            throw new TodoGroupException("TodoId는 비어있을 수 없습니다.");
        }


        Set<TodoId> newTodoIds = new HashSet<>(this.todoIds);
        newTodoIds.remove(todoId);

        return TodoGroup.builder()
                .todoGroupId(this.todoGroupId)
                .createUserId(this.createUserId)
                .userIds(this.userIds)
                .todoIds(newTodoIds)
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    // userId를 추가할 수 있다.
    public TodoGroup addUserId(UserId userId, LocalDateTime updatedAt) {
        if (userId == null) {
            throw new TodoGroupException("userId는 비어있을 수 없습니다.");
        }

        HashSet<UserId> newUserIds = new HashSet<>(this.userIds);
        newUserIds.add(userId);

        return TodoGroup.builder()
                .todoGroupId(this.todoGroupId)
                .createUserId(this.createUserId)
                .userIds(newUserIds)
                .todoIds(this.todoIds)
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    // userId를 삭제할 수 있다.
    public TodoGroup removeUserId(UserId userId, LocalDateTime updatedAt) {
        if (userId == null) {
            throw new TodoGroupException("userId는 비어있을 수 없습니다.");
        }

        if (userId.equals(this.createUserId)) {
            throw new TodoGroupException("생성자를 추방 할 수 없습니다.");
        }

        HashSet<UserId> newUserIds = new HashSet<>(this.userIds);
        newUserIds.remove(userId);

        return TodoGroup.builder()
                .todoGroupId(this.todoGroupId)
                .createUserId(this.createUserId)
                .userIds(newUserIds)
                .todoIds(this.todoIds)
                .createdAt(this.createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
