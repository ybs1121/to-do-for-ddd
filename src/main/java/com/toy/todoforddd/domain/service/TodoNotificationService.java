package com.toy.todoforddd.domain.service;

import com.toy.todoforddd.domain.model.Todo;

public interface TodoNotificationService {

    void notifyTodo(Todo todo);

    void sendNotification(Todo todo);
}
