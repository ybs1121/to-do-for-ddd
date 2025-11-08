package com.toy.todoforddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ToDoForDddApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoForDddApplication.class, args);
    }

}
