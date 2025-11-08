package com.toy.todoforddd.infrastructure.persistence.repository.post;

import com.toy.todoforddd.common.exception.TodoException;
import com.toy.todoforddd.domain.model.Todo;
import com.toy.todoforddd.domain.repository.TodoRepository;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoEntity;
import com.toy.todoforddd.infrastructure.persistence.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public Todo update(Todo todo) {
        TodoEntity todoEntity = todoMapper.toEntity(todo);
        todoEntity = todoJpaRepository.save(todoEntity);
        return todoMapper.toDomain(todoEntity);
    }

    @Override
    public void delete(Long todoId) {
        todoJpaRepository.deleteById(todoId);
    }

    @Override
    public boolean existsByAuthorIdAndTodoId(Long authorId, Long todoId) {
        return todoJpaRepository.existsByAuthorIdAndId(authorId, todoId);
    }

    @Override
    public Todo findByAuthorIdAndTodoId(Long authorId, Long todoId) {
        TodoEntity todoEntity = todoJpaRepository.findByAuthorIdAndId(authorId, todoId)
                .orElseThrow(() -> new TodoException("Not found"));
        return todoMapper.toDomain(todoEntity);
    }

    @Override
    public List<Todo> findAllIncompleteTodos() {
        return todoMapper.toDomainList(
                todoJpaRepository.findAllByCompleted(false)
        );
    }
}
