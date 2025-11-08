package com.toy.todoforddd.domain.service;

import com.toy.todoforddd.domain.model.Todo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TodoNotificationServiceImpl implements TodoNotificationService {


    @Override
    public void notifyTodo(Todo todo) {
        sendNotification(todo);
    }

    @Override
    public void sendNotification(Todo todo) {
        // 알림 미구현
        log.info("sendNotification  {}", todo);
    }
}
