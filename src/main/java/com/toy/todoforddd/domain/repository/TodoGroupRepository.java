package com.toy.todoforddd.domain.repository;

import com.toy.todoforddd.domain.model.TodoGroup;

public interface TodoGroupRepository {
    TodoGroup createTodoGroup(TodoGroup todoGroup);

    TodoGroup addTodo(TodoGroup todoGroup);

    TodoGroup addUser(TodoGroup todoGroup);

    TodoGroup removeTodo(TodoGroup todoGroup);

    TodoGroup removeUser(TodoGroup todoGroup);
}
