package com.example.todo_app.todo;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;


@Controller
@ResponseBody
class TodoController {
    private final TodoService todoService;

    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    Collection<Todo> findAll() {
        return this.todoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Todo createTodo(@Valid @RequestBody TodoDto todoDto) {
        return this.todoService.createTodo(todoDto);
    }

    @GetMapping("/{todoId}")
    Todo findById(@PathVariable int todoId) {
        var todo = this.todoService.findById(todoId);
        if (todo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo " + todoId + "not found");
        }

        return todo.get();
    }

    @PatchMapping("/{todoId}")
    Todo updateStatus(@PathVariable int todoId) {
        return this.todoService.updateStatus(todoId);
    }
    @PutMapping("/{todoId}")
    Todo updateTodo(@PathVariable int todoId,@Valid @RequestBody TodoDto todoDto){
        return  this.todoService.updateTodo(todoDto,todoId);
    }

    @DeleteMapping("/{todoId}")
    Todo deleteTodo(@PathVariable int todoId) {
        return this.todoService.deleteTodo(todoId);
    }

}
