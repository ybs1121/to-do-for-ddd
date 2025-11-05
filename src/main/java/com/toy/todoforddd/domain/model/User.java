package com.toy.todoforddd.domain.model;

import com.toy.todoforddd.presentation.controller.user.dto.UserAddRequest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {

    private Long id;
    private String userId;
    private String pw;
    private String email;

    // UserAddRequest로 User를 생성할 수 있다.
    public static User create(UserAddRequest userAddRequest) {
        return User.builder()
                .userId(userAddRequest.getUserId())
                .pw(userAddRequest.getPw())
                .email(userAddRequest.getEmail())
                .build();
    }

    // User는 Post를 생성할 수 있다.
    public Todo createPost(String title, String content, String pw, LocalDateTime createAt) {
        return Todo.builder()
                .title(title)
                .content(content)
                .pw(pw)
                .createdAt(createAt)
                .completed(false)
                .authorId(this.id)
                .build();
    }

}
