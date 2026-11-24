package com.example.todo_app.todo;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Service
class TodoService {
    private final TodoRepository todoRepository;

    TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    Todo createTodo(TodoDto createTodoDto) {
        return todoRepository.save(new Todo(null, createTodoDto.title(), createTodoDto.description(), createTodoDto.dueDate(), null, createTodoDto.priority()));
    }

    Collection<Todo> findAll(FilterDto filterDto) {

        var todos = this.todoRepository.findAll().stream();
        if (filterDto.status().isPresent()) {
            var status = filterDto.status().get();
            todos = todos.filter(s -> status == Status.COMPLETED ? s.completedAt() != null : s.completedAt() == null);
        }
        if (filterDto.search() != null) {
            todos = todos.filter(todo -> todo.title().toLowerCase().contains(filterDto.search().toLowerCase()));
        }
        return todos.toList();
    }

    Optional<Todo> findById(int id) {
        return this.todoRepository.findById(id);
    }

    Todo updateTodo(TodoDto todoDto, int todoId) {
        var todo = this.findById(todoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return this.todoRepository.save(new Todo(todo.id(), todoDto.title(), todoDto.description(), todoDto.dueDate(), todo.completedAt(), todoDto.priority()));
    }

    Todo updateStatus(int id) {
        var todo = this.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var now = Instant.now();
        Instant completedAt = todo.completedAt() == null ? now : null;
        return todoRepository.save(new Todo(todo.id(), todo.title(), todo.description(), todo.dueDate(), completedAt, todo.priority()));
    }

    Todo deleteTodo(int id) {
        var todo = this.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        todoRepository.delete(todo);
        return todo;
    }
}
