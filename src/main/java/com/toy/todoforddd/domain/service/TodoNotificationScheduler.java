package com.toy.todoforddd.domain.service;

import com.toy.todoforddd.domain.model.Todo;
import com.toy.todoforddd.domain.repository.TodoRepository;
import com.toy.todoforddd.infrastructure.persistence.repository.post.TodoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoNotificationScheduler {

    private final TodoRepository todoRepository;
    private final TodoNotificationService notificationService;

    /**
     * 9시-18시, 3시간 단위 실행: 9시, 12시, 15시, 18시에 동작
     * cron 표현식: "0 0 9,12,15,18 * * *"
     */
    @Scheduled(cron = "0 0 9,12,15,18 * * *")
    public void sendIncompleteTodoNotifications() {
        // 1. 미완료된 할 일 조회
        List<Todo> incompleteTodos = todoRepository.findAllIncompleteTodos();

        // 2. 조건에 맞는 알림 전송 (도메인 서비스에 위임)
        incompleteTodos.forEach(notificationService::notifyTodo);
    }
}
