package com.toy.todoforddd.infrastructure.persistence.repository;

import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoGroupJpaRepository extends JpaRepository<TodoGroupEntity,Long> {
}
