package com.toy.todoforddd.infrastructure.persistence.repository.user;

import com.toy.todoforddd.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
