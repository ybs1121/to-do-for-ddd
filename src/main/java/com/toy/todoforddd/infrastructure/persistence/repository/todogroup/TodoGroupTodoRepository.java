
package com.toy.todoforddd.infrastructure.persistence.repository.todogroup;

import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupTodoEntity;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoGroupTodoRepository extends JpaRepository<TodoGroupTodoEntity, Long> {
    List<TodoGroupTodoEntity> findByTodoGroupId(Long todoGroupId);
}
