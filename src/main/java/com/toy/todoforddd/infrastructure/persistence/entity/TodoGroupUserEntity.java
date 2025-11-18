package com.toy.todoforddd.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "todo_group_user")
public class TodoGroupUserEntity {
    @Id
    @GeneratedValue
    private Long id;

    // @JoinColumn으로 인해 여기에 FK 컬럼이 생김
    @Column(name = "todo_group_id")
    private Long todoGroupId;


}
