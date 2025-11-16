package com.toy.todoforddd.domain.model;

import com.toy.todoforddd.common.exception.TodoGroupException;
import com.toy.todoforddd.domain.model.key.TodoGroupId;
import com.toy.todoforddd.domain.model.key.TodoId;
import com.toy.todoforddd.domain.model.key.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("TodoGroup 테스트")
class TodoGroupTest {

    private TodoGroup todoGroup;
    private UserId createUserId;
    private TodoId todoId1;
    private TodoId todoId2;
    private UserId userId1;
    private UserId userId2;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        createUserId = new UserId(1L);
        todoId1 = new TodoId(1L);
        todoId2 = new TodoId(2L);
        userId1 = new UserId(2L);
        userId2 = new UserId(3L);

        todoGroup = TodoGroup.createTodoGroup(createUserId, now);
    }

    // ===== TodoGroup 생성 테스트 =====
    @Nested
    @DisplayName("TodoGroup 생성")
    class CreateTodoGroup {

        @Test
        @DisplayName("TodoGroup을 생성할 수 있다")
        void success() {
            // When
            TodoGroup group = TodoGroup.createTodoGroup(createUserId, now);

            // Then
            assertThat(group)
                    .satisfies(g -> {
                        assertThat(g.getCreateUserId()).isEqualTo(createUserId);
                        assertThat(g.getCreatedAt()).isEqualTo(now);
                        assertThat(g.getUserIds()).contains(createUserId);
                        assertThat(g.getTodoIds()).isEmpty();
                    });
        }

        @Test
        @DisplayName("null createUserId로는 TodoGroup을 생성할 수 없다")
        void failWithNullCreateUserId() {
            // When & Then
            assertThatThrownBy(() -> TodoGroup.createTodoGroup(null, now))
                    .isInstanceOf(TodoGroupException.class);
        }

        @Test
        @DisplayName("null createdAt로는 TodoGroup을 생성할 수 없다")
        void failWithNullCreatedAt() {
            // When & Then
            assertThatThrownBy(() -> TodoGroup.createTodoGroup(createUserId, null))
                    .isInstanceOf(TodoGroupException.class);
        }
    }

    // ===== Todo 추가 테스트 =====
    @Nested
    @DisplayName("Todo 추가")
    class AddTodo {

        @Test
        @DisplayName("TodoGroup에 Todo를 추가할 수 있다")
        void success() {
            // When
            TodoGroup updated = todoGroup.addTodo(todoId1, now);

            // Then
            assertThat(updated.getTodoIds())
                    .contains(todoId1)
                    .hasSize(1);
        }

        @Test
        @DisplayName("null TodoId를 추가할 수 없다")
        void failWithNullTodoId() {
            // When & Then
            assertThatThrownBy(() -> todoGroup.addTodo(null, now))
                    .isInstanceOf(TodoGroupException.class)
                    .hasMessage("TodoId는 비어있을 수 없습니다");
        }

        @Test
        @DisplayName("이미 존재하는 Todo를 다시 추가할 수 없다")
        void failWithDuplicateTodo() {
            // Given
            TodoGroup updated = todoGroup.addTodo(todoId1, now);

            // When & Then
            assertThatThrownBy(() -> updated.addTodo(todoId1, now))
                    .isInstanceOf(TodoGroupException.class)
                    .hasMessage("이미 그룹에 추가된 Todo입니다");
        }

        @Test
        @DisplayName("여러 개의 Todo를 추가할 수 있다")
        void addMultipleTodos() {
            // When
            TodoGroup step1 = todoGroup.addTodo(todoId1, now);
            TodoGroup step2 = step1.addTodo(todoId2, now);

            // Then
            assertThat(step2.getTodoIds())
                    .contains(todoId1, todoId2)
                    .hasSize(2);
        }

        @Test
        @DisplayName("updatedAt이 올바르게 설정된다")
        void updatedAtIsSet() {
            // Given
            LocalDateTime later = now.plusHours(1);

            // When
            TodoGroup updated = todoGroup.addTodo(todoId1, later);

            // Then
            assertThat(updated.getUpdatedAt()).isEqualTo(later);
        }

        @Test
        @DisplayName("기존 객체는 변경되지 않는다 (불변성)")
        void immutability() {
            // When
            TodoGroup updated = todoGroup.addTodo(todoId1, now);

            // Then
            assertThat(todoGroup.getTodoIds()).isEmpty();
            assertThat(updated.getTodoIds()).contains(todoId1);
        }
    }

    // ===== Todo 제거 테스트 =====
    @Nested
    @DisplayName("Todo 제거")
    class RemoveTodo {

        @Test
        @DisplayName("TodoGroup에서 Todo를 제거할 수 있다")
        void success() {
            // Given
            TodoGroup updated = todoGroup.addTodo(todoId1, now);

            // When
            TodoGroup removed = updated.removeTodo(todoId1, now);

            // Then
            assertThat(removed.getTodoIds())
                    .doesNotContain(todoId1)
                    .isEmpty();
        }

        @Test
        @DisplayName("null TodoId를 제거할 수 없다")
        void failWithNullTodoId() {
            // When & Then
            assertThatThrownBy(() -> todoGroup.removeTodo(null, now))
                    .isInstanceOf(TodoGroupException.class)
                    .hasMessage("TodoId는 비어있을 수 없습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 Todo를 제거해도 예외가 발생하지 않는다")
        void removeNonExistentTodo() {
            // When & Then
            assertThatCode(() -> todoGroup.removeTodo(todoId1, now))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("updatedAt이 올바르게 설정된다")
        void updatedAtIsSet() {
            // Given
            TodoGroup updated = todoGroup.addTodo(todoId1, now);
            LocalDateTime later = now.plusHours(1);

            // When
            TodoGroup removed = updated.removeTodo(todoId1, later);

            // Then
            assertThat(removed.getUpdatedAt()).isEqualTo(later);
        }

        @Test
        @DisplayName("기존 객체는 변경되지 않는다 (불변성)")
        void immutability() {
            // Given
            TodoGroup updated = todoGroup.addTodo(todoId1, now);

            // When
            TodoGroup removed = updated.removeTodo(todoId1, now);

            // Then
            assertThat(updated.getTodoIds()).contains(todoId1);
            assertThat(removed.getTodoIds()).isEmpty();
        }
    }

    // ===== 사용자 추가 테스트 =====
    @Nested
    @DisplayName("사용자 추가")
    class AddUser {

        @Test
        @DisplayName("TodoGroup에 사용자를 추가할 수 있다")
        void success() {
            // When
            TodoGroup updated = todoGroup.addUserId(userId1, now);

            // Then
            assertThat(updated.getUserIds())
                    .contains(createUserId, userId1)
                    .hasSize(2);
        }

        @Test
        @DisplayName("null UserId를 추가할 수 없다")
        void failWithNullUserId() {
            // When & Then
            assertThatThrownBy(() -> todoGroup.addUserId(null, now))
                    .isInstanceOf(TodoGroupException.class)
                    .hasMessage("userId는 비어있을 수 없습니다.");
        }


        @Test
        @DisplayName("여러 사용자를 추가할 수 있다")
        void addMultipleUsers() {
            // When
            TodoGroup step1 = todoGroup.addUserId(userId1, now);
            TodoGroup step2 = step1.addUserId(userId2, now);

            // Then
            assertThat(step2.getUserIds())
                    .contains(createUserId, userId1, userId2)
                    .hasSize(3);
        }

        @Test
        @DisplayName("updatedAt이 올바르게 설정된다")
        void updatedAtIsSet() {
            // Given
            LocalDateTime later = now.plusHours(1);

            // When
            TodoGroup updated = todoGroup.addUserId(userId1, later);

            // Then
            assertThat(updated.getUpdatedAt()).isEqualTo(later);
        }

        @Test
        @DisplayName("기존 객체는 변경되지 않는다 (불변성)")
        void immutability() {
            // When
            TodoGroup updated = todoGroup.addUserId(userId1, now);

            // Then
            assertThat(todoGroup.getUserIds()).contains(createUserId);
            assertThat(todoGroup.getUserIds()).doesNotContain(userId1);
            assertThat(updated.getUserIds()).contains(createUserId, userId1);
        }
    }

    // ===== 사용자 제거 테스트 =====
    @Nested
    @DisplayName("사용자 제거")
    class RemoveUser {

        @Test
        @DisplayName("TodoGroup에서 사용자를 제거할 수 있다")
        void success() {
            // Given
            TodoGroup updated = todoGroup.addUserId(userId1, now);

            // When
            TodoGroup removed = updated.removeUserId(userId1, now);

            // Then
            assertThat(removed.getUserIds())
                    .doesNotContain(userId1)
                    .contains(createUserId);
        }

        @Test
        @DisplayName("null UserId를 제거할 수 없다")
        void failWithNullUserId() {
            // When & Then
            assertThatThrownBy(() -> todoGroup.removeUserId(null, now))
                    .isInstanceOf(TodoGroupException.class)
                    .hasMessage("userId는 비어있을 수 없습니다.");
        }

        @Test
        @DisplayName("생성자는 제거할 수 없다")
        void failRemoveCreator() {
            // When & Then
            assertThatThrownBy(() -> todoGroup.removeUserId(createUserId, now))
                    .isInstanceOf(TodoGroupException.class)
                    .hasMessage("생성자를 추방 할 수 없습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 사용자를 제거해도 예외가 발생하지 않는다")
        void removeNonExistentUser() {
            // When & Then
            assertThatCode(() -> todoGroup.removeUserId(userId1, now))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("updatedAt이 올바르게 설정된다")
        void updatedAtIsSet() {
            // Given
            TodoGroup updated = todoGroup.addUserId(userId1, now);
            LocalDateTime later = now.plusHours(1);

            // When
            TodoGroup removed = updated.removeUserId(userId1, later);

            // Then
            assertThat(removed.getUpdatedAt()).isEqualTo(later);
        }

        @Test
        @DisplayName("기존 객체는 변경되지 않는다 (불변성)")
        void immutability() {
            // Given
            TodoGroup updated = todoGroup.addUserId(userId1, now);

            // When
            TodoGroup removed = updated.removeUserId(userId1, now);

            // Then
            assertThat(updated.getUserIds()).contains(userId1);
            assertThat(removed.getUserIds()).doesNotContain(userId1);
        }
    }

    // ===== 복합 시나리오 테스트 =====
    @Nested
    @DisplayName("복합 시나리오")
    class ComplexScenarios {

        @Test
        @DisplayName("여러 사용자와 여러 Todo를 함께 관리할 수 있다")
        void manageMultipleUsersAndTodos() {
            // When
            TodoGroup step1 = todoGroup.addUserId(userId1, now);
            TodoGroup step2 = step1.addUserId(userId2, now);
            TodoGroup step3 = step2.addTodo(todoId1, now);
            TodoGroup step4 = step3.addTodo(todoId2, now);

            // Then
            assertThat(step4.getUserIds())
                    .contains(createUserId, userId1, userId2)
                    .hasSize(3);
            assertThat(step4.getTodoIds())
                    .contains(todoId1, todoId2)
                    .hasSize(2);
        }

        @Test
        @DisplayName("추가 후 제거 후 다시 추가할 수 있다")
        void addRemoveAdd() {
            // When
            TodoGroup added = todoGroup.addTodo(todoId1, now);
            TodoGroup removed = added.removeTodo(todoId1, now);
            TodoGroup readded = removed.addTodo(todoId1, now);

            // Then
            assertThat(readded.getTodoIds()).contains(todoId1);
        }

        @Test
        @DisplayName("시간 흐름에 따라 updatedAt이 변경된다")
        void updatedAtChangesOverTime() {
            // Given
            LocalDateTime t1 = now;
            LocalDateTime t2 = now.plusHours(1);
            LocalDateTime t3 = now.plusHours(2);

            // When
            TodoGroup after1stOperation = todoGroup.addTodo(todoId1, t1);
            TodoGroup after2ndOperation = after1stOperation.addUserId(userId1, t2);
            TodoGroup after3rdOperation = after2ndOperation.removeTodo(todoId1, t3);

            // Then
            assertThat(after1stOperation.getUpdatedAt()).isEqualTo(t1);
            assertThat(after2ndOperation.getUpdatedAt()).isEqualTo(t2);
            assertThat(after3rdOperation.getUpdatedAt()).isEqualTo(t3);
        }

        @Test
        @DisplayName("createdAt은 항상 동일하게 유지된다")
        void createdAtNeverChanges() {
            // When
            TodoGroup after1stOp = todoGroup.addTodo(todoId1, now.plusHours(1));
            TodoGroup after2ndOp = after1stOp.addUserId(userId1, now.plusHours(2));

            // Then
            assertThat(after1stOp.getCreatedAt()).isEqualTo(now);
            assertThat(after2ndOp.getCreatedAt()).isEqualTo(now);
        }

        @Test
        @DisplayName("모든 정보가 올바르게 유지된다")
        void allDataPreserved() {
            // When
            TodoGroup updated = todoGroup
                    .addTodo(todoId1, now)
                    .addUserId(userId1, now)
                    .addTodo(todoId2, now);

            // Then
            assertThat(updated)
                    .satisfies(g -> {
                        assertThat(g.getTodoGroupId()).isEqualTo(todoGroup.getTodoGroupId());
                        assertThat(g.getCreateUserId()).isEqualTo(createUserId);
                        assertThat(g.getCreatedAt()).isEqualTo(now);
                        assertThat(g.getTodoIds()).contains(todoId1, todoId2);
                        assertThat(g.getUserIds()).contains(createUserId, userId1);
                    });
        }
    }
}