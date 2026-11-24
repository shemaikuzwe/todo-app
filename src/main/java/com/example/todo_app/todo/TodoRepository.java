package com.example.todo_app.todo;


import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
interface TodoRepository extends ListCrudRepository<Todo, Integer> {
}


record Todo(@Id Long id, String title,
            String description,
            Instant dueDate,
            Instant completedAt,
            Integer priority) {}