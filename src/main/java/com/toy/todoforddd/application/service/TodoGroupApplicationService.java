package com.toy.todoforddd.application.service;

import com.toy.todoforddd.domain.model.Todo;
import com.toy.todoforddd.domain.model.TodoGroup;
import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.TodoId;
import com.toy.todoforddd.domain.model.key.UserId;
import com.toy.todoforddd.domain.repository.TodoGroupRepository;
import com.toy.todoforddd.domain.repository.TodoRepository;
import com.toy.todoforddd.presentation.controller.todogroup.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoGroupApplicationService {

    private final TodoGroupRepository todoGroupRepository;
    private final TodoRepository todoRepository;


    // todoGroup 생성
    public TodoGroupResponse createTodoGroup(TodoGroupAddRequest todoGroupAddRequest) {

        TodoGroup todoGroup = TodoGroup.createTodoGroup(new UserId(todoGroupAddRequest.getCreateUserId()), LocalDateTime.now());
        TodoGroup saveTodoGroup = todoGroupRepository.createTodoGroup(todoGroup);

        return TodoGroupResponse.toTodoGroupResponse(saveTodoGroup);
    }


    // todo 추가

    public TodoGroupResponse addTodo(TodoGroupAddTodoRequest todoGroupAddTodoRequest) {
        TodoGroup todoGroup = todoGroupRepository.findTodoGroup(new TodoGroupId(todoGroupAddTodoRequest.getTodoGroupId()));

        // todo 저장
        Todo todo = Todo.create(todoGroupAddTodoRequest.getAuthorId(), todoGroupAddTodoRequest.getContent(), LocalDateTime.now());
        Todo saveTodo = todoRepository.save(todo);

        TodoGroup addTodoGroup = todoGroup.addTodo(new TodoId(saveTodo.getId()), LocalDateTime.now());
        TodoGroup updateTodoGroup = todoGroupRepository.addTodo(addTodoGroup, new TodoId(saveTodo.getId()));
        return TodoGroupResponse.toTodoGroupResponse(updateTodoGroup);

    }

    // todo 삭제
    public TodoGroupResponse removeTodo(TodoGroupDeleteTodoRequest todoGroupDeleteTodoRequest) {
        TodoGroup todoGroup = todoGroupRepository.findTodoGroup(new TodoGroupId(todoGroupDeleteTodoRequest.getTodoGroupId()));

        // todo 삭제
        TodoGroup deleteTodoGroup = todoGroup.removeTodo(new TodoId(todoGroupDeleteTodoRequest.getTodoId()), LocalDateTime.now());

        TodoGroup deletedTodoGroup = todoGroupRepository.removeTodo(deleteTodoGroup, new TodoId(todoGroupDeleteTodoRequest.getTodoId()));
        todoRepository.delete(todoGroupDeleteTodoRequest.getTodoId());

        return TodoGroupResponse.toTodoGroupResponse(deletedTodoGroup);

    }


    // user 추가
    public TodoGroupResponse addUser(TodoGroupAddUserRequest todoGroupAddUserRequest) {
        TodoGroup todoGroup = todoGroupRepository.findTodoGroup(new TodoGroupId(todoGroupAddUserRequest.getTodoGroupId()));

        TodoGroup addTodoGroup = todoGroup.addUserId(new UserId(todoGroupAddUserRequest.getUserId()), LocalDateTime.now());
        TodoGroup updateTodoGroup = todoGroupRepository.addUser(addTodoGroup, new UserId(todoGroupAddUserRequest.getUserId()));
        return TodoGroupResponse.toTodoGroupResponse(updateTodoGroup);

    }

    // user 삭제

    public TodoGroupResponse removeUser(TodoGroupDeleteUserRequest todoGroupDeleteUserRequest) {
        TodoGroup todoGroup = todoGroupRepository.findTodoGroup(new TodoGroupId(todoGroupDeleteUserRequest.getTodoGroupId()));

        TodoGroup addTodoGroup = todoGroup.removeUserId(new UserId(todoGroupDeleteUserRequest.getUserId()), LocalDateTime.now());
        TodoGroup updateTodoGroup = todoGroupRepository.removeUser(addTodoGroup, new UserId(todoGroupDeleteUserRequest.getUserId()));
        return TodoGroupResponse.toTodoGroupResponse(updateTodoGroup);
    }


    // 조회
    public TodoGroupResponse getTodoGroup(Long todoGroupId) {
        return TodoGroupResponse.toTodoGroupResponse(todoGroupRepository.findTodoGroup(new TodoGroupId(todoGroupId)));
    }

    public void deleteTodoGroup(Long todoGroupId) {
        TodoGroup todoGroup = todoGroupRepository.findTodoGroup(new TodoGroupId(todoGroupId));
        todoGroupRepository.deleteTodoGroup(todoGroup);
    }
}
