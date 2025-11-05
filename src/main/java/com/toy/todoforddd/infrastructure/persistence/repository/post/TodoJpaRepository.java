package com.toy.todoforddd.infrastructure.persistence.repository.post;

import com.toy.todoforddd.infrastructure.persistence.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, Long> {
}
