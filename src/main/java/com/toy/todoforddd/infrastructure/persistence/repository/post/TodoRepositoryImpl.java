package com.toy.todoforddd.infrastructure.persistence.repository.post;

import com.toy.todoforddd.domain.model.Todo;
import com.toy.todoforddd.domain.repository.TodoRepository;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoEntity;
import com.toy.todoforddd.infrastructure.persistence.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final TodoJpaRepository todoJpaRepository;
    private final TodoMapper todoMapper;


    @Override
    public Todo save(Todo todo) {
        TodoEntity todoEntity = todoMapper.toEntity(todo);
        todoEntity = todoJpaRepository.save(todoEntity);
        return todoMapper.toDomain(todoEntity);
    }
}
