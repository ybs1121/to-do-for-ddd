package com.toy.todoforddd.infrastructure.persistence.repository;

import com.toy.todoforddd.common.exception.TodoGroupException;
import com.toy.todoforddd.domain.model.TodoGroup;
import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.TodoId;
import com.toy.todoforddd.domain.model.key.UserId;
import com.toy.todoforddd.domain.repository.TodoGroupRepository;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupEntity;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupTodoEntity;
import com.toy.todoforddd.infrastructure.persistence.entity.TodoGroupUserEntity;
import com.toy.todoforddd.infrastructure.persistence.mapper.TodoGroupMapper;
import com.toy.todoforddd.infrastructure.persistence.mapper.TodoGroupTodoMapper;
import com.toy.todoforddd.infrastructure.persistence.mapper.TodoGroupUserMapper;
import com.toy.todoforddd.infrastructure.persistence.repository.todogroup.TodoGroupTodoRepository;
import com.toy.todoforddd.infrastructure.persistence.repository.todogroup.TodoGroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class TodoGroupRepositoryImpl implements TodoGroupRepository {

    private final TodoGroupJpaRepository todoGroupJpaRepository;
    private final TodoGroupUserRepository todoGroupUserRepository;
    private final TodoGroupTodoRepository todoGroupTodoRepository;


    private final TodoGroupMapper todoGroupMapper;
    private final TodoGroupTodoMapper todoGroupTodoMapper;
    private final TodoGroupUserMapper todoGroupUserMapper;

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
    public TodoGroup addTodo(TodoGroup todoGroup, TodoId todoId) {
        // todoGroup 가져오기

        TodoGroupEntity todoGroupEntity = todoGroupJpaRepository.findById(todoGroup.getTodoGroupId().id()).orElseThrow(
                () -> new TodoGroupException("TodoGroup을 찾을 수 업습니다.")
        );

        // todo mapping 저장
        TodoGroupTodoEntity todoGroupTodoEntity = todoGroupTodoMapper.toEntity(todoId, todoGroup.getTodoGroupId());
        TodoGroupTodoEntity saveTodoGroupTodoEntity = todoGroupTodoRepository.save(todoGroupTodoEntity);

        // mapping 정보 가져오기

        List<TodoGroupUserEntity> todoGroupUserEntities = todoGroupUserRepository.findByTodoGroupId(todoGroupEntity.getTodoGroupId());
        List<TodoGroupTodoEntity> todoGroupTodoEntities = todoGroupTodoRepository.findByTodoGroupId(todoGroupEntity.getTodoGroupId());

        // return

        return todoGroupMapper.toDomain(todoGroupEntity, todoGroupUserEntities, todoGroupTodoEntities);
    }

    @Override
    public TodoGroup addUser(TodoGroup todoGroup, UserId userId) {
        // todoGroup 가져오기

        TodoGroupEntity todoGroupEntity = todoGroupJpaRepository.findById(todoGroup.getTodoGroupId().id()).orElseThrow(
                () -> new TodoGroupException("TodoGroup을 찾을 수 업습니다.")
        );

        todoGroupUserRepository.save(todoGroupUserMapper.toEntity(userId, todoGroup.getTodoGroupId()));

        // mapping 정보 가져오기

        List<TodoGroupUserEntity> todoGroupUserEntities = todoGroupUserRepository.findByTodoGroupId(todoGroupEntity.getTodoGroupId());
        List<TodoGroupTodoEntity> todoGroupTodoEntities = todoGroupTodoRepository.findByTodoGroupId(todoGroupEntity.getTodoGroupId());

        // return

        return todoGroupMapper.toDomain(todoGroupEntity, todoGroupUserEntities, todoGroupTodoEntities);
    }

    @Override
    public TodoGroup removeTodo(TodoGroup todoGroup, TodoId todoId) {

        TodoGroupEntity todoGroupEntity = todoGroupJpaRepository.findById(todoGroup.getTodoGroupId().id()).orElseThrow(
                () -> new TodoGroupException("TodoGroup을 찾을 수 업습니다.")
        );

        TodoGroupTodoEntity todoGroupTodoEntity = todoGroupTodoRepository.findById(todoId.id()).orElseThrow(
                () -> new TodoGroupException("Todo를 찾을 수 업습니다.")
        );

        todoGroupTodoRepository.delete(todoGroupTodoEntity);

        // mapping 정보 가져오기

        List<TodoGroupUserEntity> todoGroupUserEntities = todoGroupUserRepository.findByTodoGroupId(todoGroupEntity.getTodoGroupId());
        List<TodoGroupTodoEntity> todoGroupTodoEntities = todoGroupTodoRepository.findByTodoGroupId(todoGroupEntity.getTodoGroupId());

        // return

        return todoGroupMapper.toDomain(todoGroupEntity, todoGroupUserEntities, todoGroupTodoEntities);
    }

    @Override
    public TodoGroup removeUser(TodoGroup todoGroup, UserId userId) {

        TodoGroupEntity todoGroupEntity = todoGroupJpaRepository.findById(todoGroup.getTodoGroupId().id()).orElseThrow(
                () -> new TodoGroupException("TodoGroup을 찾을 수 업습니다.")
        );

        TodoGroupUserEntity todoGroupUserEntity = todoGroupUserRepository.findById(userId.id()).orElseThrow(
                () -> new TodoGroupException("User를 찾을 수 업습니다.")
        );

        todoGroupUserRepository.delete(todoGroupUserEntity);

        List<TodoGroupUserEntity> todoGroupUserEntities = todoGroupUserRepository.findByTodoGroupId(todoGroupEntity.getTodoGroupId());
        List<TodoGroupTodoEntity> todoGroupTodoEntities = todoGroupTodoRepository.findByTodoGroupId(todoGroupEntity.getTodoGroupId());

        // return

        return todoGroupMapper.toDomain(todoGroupEntity, todoGroupUserEntities, todoGroupTodoEntities);
    }

    @Override
    public void deleteTodoGroup(TodoGroup todoGroup) {
        // Mapping 정보 삭제
        todoGroupUserRepository.deleteAllByIdInBatch(todoGroup.getUserIds().stream().map(UserId::id).collect(Collectors.toSet()));
        todoGroupTodoRepository.deleteAllByIdInBatch(todoGroup.getTodoIds().stream().map(TodoId::id).collect(Collectors.toSet()));

        // TodoGroup 삭제
        todoGroupJpaRepository.deleteById(todoGroup.getTodoGroupId().id());
    }

    @Override
    public TodoGroup findTodoGroup(TodoGroupId todoGroupId) {
        TodoGroupEntity entity = todoGroupJpaRepository
                .findById(todoGroupId.id())
                .orElseThrow(() -> new TodoGroupException("TodoGroup을 찾을 수 업습니다."));

        List<TodoGroupUserEntity> users = todoGroupUserRepository
                .findByTodoGroupId(todoGroupId.id());
        List<TodoGroupTodoEntity> todos = todoGroupTodoRepository
                .findByTodoGroupId(todoGroupId.id());

        return todoGroupMapper.toDomain(entity, users, todos);
    }
}


