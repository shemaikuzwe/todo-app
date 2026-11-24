package com.example.todo_app.todo;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.Optional;

record TodoDto(
        @NotBlank(message = "title  is required")
        @Size(min=3,max=50)
        String title,

        @NotBlank(message = "please provide a valid description")
        @Size(min = 3,max = 500)
        //java implicit null,if omitted
        String description,

        //java implicit null,if omitted
        @Future
        Instant dueDate,
        @Min(0) @Max(10)
        Integer priority
) { }

