package com.toy.todoforddd.infrastructure.persistence.entity;

import com.toy.todoforddd.domain.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String pw;
    private String email;

    public UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.userId = user.getUserId();
        userEntity.pw = user.getPw();
        userEntity.email = user.getEmail();
        return userEntity;
    }

    public User to() {
        return User.builder()
                .id(id)
                .userId(userId)
                .pw(pw)
                .email(email)
                .build();
    }

}
