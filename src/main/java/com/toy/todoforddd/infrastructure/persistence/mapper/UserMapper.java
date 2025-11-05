package com.toy.todoforddd.infrastructure.persistence.mapper;

import com.toy.todoforddd.domain.model.User;
import com.toy.todoforddd.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setUserId(user.getUserId());
        userEntity.setPw(user.getPw());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }

    // Entity → Domain
    public User toDomain(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .pw(userEntity.getPw())
                .email(userEntity.getEmail())
                .build();
    }

}
