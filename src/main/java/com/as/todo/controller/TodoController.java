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
        return ResponseEntity.ok(todoService.createTodo(todoItem, email));
    }

    @PutMapping("/update/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> modifyTodo(@Valid @RequestBody TodoItem todoItem,  @PathVariable String email) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.updateTodo(todoItem, email));
    }

    @PutMapping("/modify-category")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> modifyCategory(@RequestParam(value = "category", required = true) String category, @RequestParam(value = "id", required = true) Long id) {
        if(category == null || category.equals("")) {
            category = "Default";
            todoService.modifyCategory(category, id);
            return ResponseEntity.ok(new MessageResponse("You did not provide category. Its updated to default."));
        }
        todoService.modifyCategory(category, id);
        return ResponseEntity.ok(new MessageResponse("Category modified"));
    }

    @GetMapping("/list_todos_by_user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> todosByUser(@RequestParam(value = "email", required = true) String email,
                                         @RequestParam(value = "filterType", required = false) String filterType,
                                         @RequestParam(value = "filterValue", required = false) String filterValue) {
        return ResponseEntity.ok(todoService.todosByUser(email, filterType, filterValue));
    }
}
