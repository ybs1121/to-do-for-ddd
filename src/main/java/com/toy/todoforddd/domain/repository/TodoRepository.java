package com.toy.todoforddd.domain.repository;

import com.toy.todoforddd.domain.model.Todo;

public interface TodoRepository {

    Todo save(Todo todo);
}
