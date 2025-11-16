package com.toy.todoforddd.domain.model.key;

import com.toy.todoforddd.common.exception.TodoGroupException;

public record TodoGroupId(Long id) {
    public TodoGroupId (Long id) {
        if (id == null || id <= 0) {
            throw new TodoGroupException("TodoGroupId는 양수여야 합니다");
        }
        this.id = id;
    }
}
