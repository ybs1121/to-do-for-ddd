package com.toy.todoforddd.domain.model.key;

import com.toy.todoforddd.common.exception.UserException;

public record UserId(Long id) {
    public UserId(Long id) {
        if (id == null || id <= 0) {
            throw new UserException("UserId는 양수여야 합니다");
        }
        this.id = id;
    }
}
