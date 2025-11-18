package com.toy.todoforddd.application.service;

import com.toy.todoforddd.domain.repository.TodoGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoGroupApplicationService {

    private final TodoGroupRepository todoGroupRepository;
}
