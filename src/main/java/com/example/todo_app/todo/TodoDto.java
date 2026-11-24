package com.example.todo_app.todo;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Optional;

record TodoDto(
        @NotBlank(message = "title  is required")
        @Size(min=3,max=50)
        String title,

        @NotBlank(message = "please provide a valid description")
        @Size(min = 3,max = 200)
        //java implicit null,if omitted
        String description,

        //java implicit null,if omitted
        Instant dueDate,
        @Min(0) @Max(10)
        Integer priority
) { }

