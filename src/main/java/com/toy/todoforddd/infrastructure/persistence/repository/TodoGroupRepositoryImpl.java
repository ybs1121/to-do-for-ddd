package com.toy.todoforddd.infrastructure.persistence.repository;

import com.toy.todoforddd.domain.model.TodoGroup;
import com.toy.todoforddd.domain.repository.TodoGroupRepository;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupEntity;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupTodoEntity;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupUserEntity;
import com.toy.todoforddd.infrastructure.persistence.mapper.TodoGroupMapper;
import com.toy.todoforddd.infrastructure.persistence.repository.todogroup.TodoGroupTodoRepository;
import com.toy.todoforddd.infrastructure.persistence.repository.todogroup.TodoGroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class TodoGroupRepositoryImpl implements TodoGroupRepository {

    private final TodoGroupJpaRepository todoGroupJpaRepository;
    private final TodoGroupUserRepository todoGroupUserRepository;
    private final TodoGroupTodoRepository todoGroupTodoRepository;


    private final TodoGroupMapper todoGroupMapper;

    @Override
    public TodoGroup createTodoGroup(TodoGroup todoGroup) {
        TodoGroupEntity todoGroupEntity = todoGroupMapper.toEntity(todoGroup);
        todoGroupEntity = todoGroupJpaRepository.save(todoGroupEntity);

        List<TodoGroupUserEntity> users = todoGroupEntity.getUsers();
        List<TodoGroupUserEntity> todoGroupUserEntities = todoGroupUserRepository.saveAll(users);

        List<TodoGroupTodoEntity> todos = todoGroupEntity.getTodos();
        List<TodoGroupTodoEntity> todoGroupTodoEntities = todoGroupTodoRepository.saveAll(todos);

        return todoGroupMapper.toDomain(todoGroupEntity, todoGroupUserEntities, todoGroupTodoEntities);
    }

    @Override
    public TodoGroup addTodo(TodoGroup todoGroup) {
        return null;
    }

    @Override
    public TodoGroup addUser(TodoGroup todoGroup) {
        return null;
    }

    @Override
    public TodoGroup removeTodo(TodoGroup todoGroup) {
        return null;
    }

    @Override
    public TodoGroup removeUser(TodoGroup todoGroup) {
        return null;
    }
}
