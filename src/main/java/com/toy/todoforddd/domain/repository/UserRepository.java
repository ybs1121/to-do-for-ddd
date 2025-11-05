package com.toy.todoforddd.domain.repository;

import com.toy.todoforddd.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    User getById(Long id);

}
