package com.toy.todoforddd.infrastructure.persistence.repository.user;

import com.toy.todoforddd.domain.model.User;
import com.toy.todoforddd.domain.repository.UserRepository;
import com.toy.todoforddd.infrastructure.persistence.entity.UserEntity;
import com.toy.todoforddd.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userJpaRepository.save(userMapper.toEntity(user));
        return userMapper.toDomain(userEntity);
    }

    @Override
    public User getById(Long id) {
        UserEntity userEntity = userJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("User not find"));
        return userMapper.toDomain(userEntity);
    }
}
