package com.toy.todoforddd.application.service.fake;

import com.toy.todoforddd.domain.model.User;
import com.toy.todoforddd.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public User save(User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("User not find"));
    }

    public void clear() {
        counter.set(0);
        users.clear();
    }
}
