package com.toy.todoforddd.domain.repository;

import com.toy.todoforddd.domain.model.TodoGroup;
import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.TodoId;
import com.toy.todoforddd.domain.model.key.UserId;

public interface TodoGroupRepository {
    TodoGroup createTodoGroup(TodoGroup todoGroup);

    TodoGroup addTodo(TodoGroup todoGroup, TodoId todoId);

    TodoGroup addUser(TodoGroup todoGroup, UserId userId);

    TodoGroup removeTodo(TodoGroup todoGroup, TodoId todoId);

    TodoGroup removeUser(TodoGroup todoGroup, UserId userId);

    void deleteTodoGroup(TodoGroup todoGroup);

    TodoGroup findTodoGroup(TodoGroupId todoGroupId);
}
