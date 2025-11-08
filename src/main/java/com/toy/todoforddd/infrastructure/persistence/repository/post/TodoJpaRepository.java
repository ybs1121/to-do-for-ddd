package com.toy.todoforddd.infrastructure.persistence.repository.post;

import com.toy.todoforddd.infrastructure.persistence.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, Long> {
    boolean existsByAuthorIdAndId(Long authorId, Long todoId);

    Optional<TodoEntity> findByAuthorIdAndId(Long authorId, Long todoId);

    List<TodoEntity> findAllByCompleted(Boolean completed);
}
