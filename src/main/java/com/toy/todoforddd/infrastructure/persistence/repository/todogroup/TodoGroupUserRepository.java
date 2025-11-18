package com.toy.todoforddd.infrastructure.persistence.repository.todogroup;

import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoGroupUserRepository extends JpaRepository<TodoGroupUserEntity,Long> {
}
