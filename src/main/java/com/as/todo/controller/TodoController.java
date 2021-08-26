package com.as.todo.controller;

import com.as.todo.entity.TodoItem;
import com.as.todo.payload.response.MessageResponse;
import com.as.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/create/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createTodo(@Valid @RequestBody TodoItem todoItem, @PathVariable String email) {
        todoService.createTodo(todoItem, email, false);
        return ResponseEntity.ok(new MessageResponse("ToDo Created."));
    }

    @PutMapping("/update/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> modifyTodo(@Valid @RequestBody TodoItem todoItem,  @PathVariable String email) {
        todoService.createTodo(todoItem, email, true);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Todo updated"));
    }
}
