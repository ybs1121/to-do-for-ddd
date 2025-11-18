
package com.toy.todoforddd.infrastructure.persistence.repository.todogroup;

import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupTodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoGroupTodoRepository extends JpaRepository<TodoGroupTodoEntity,Long> {
}
