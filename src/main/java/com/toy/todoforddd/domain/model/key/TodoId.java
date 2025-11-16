package com.toy.todoforddd.domain.model.key;

import com.toy.todoforddd.common.exception.TodoException;

public record TodoId(
        Long id
) {
    public TodoId(Long id) {
        if (id == null || id <= 0) {
            throw new TodoException("TodoId는 양수여야 합니다");
        }
        this.id = id;
    }
}
