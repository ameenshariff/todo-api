package com.as.todo.service;

import com.as.todo.entity.TodoItem;
import com.as.todo.entity.UserRegistration;
import com.as.todo.repository.TodoRepo;
import com.as.todo.repository.UserRegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private UserRegistrationRepo userRegistrationRepo;

    public void createTodo(TodoItem todoItem, String email, boolean update) {
        if(update) {
            Optional<TodoItem> todoToBeUpdated = todoRepo.findById(todoItem.getId());
            if(todoToBeUpdated.isPresent()) {
                todoItem.setCreatedDate(todoToBeUpdated.get().getCreatedDate());
                todoItem.setUpdateDate(new Date());
            }
        }
        if(todoItem.getCategory() == null || todoItem.getCategory().equals(""))
            todoItem.setCategory("Default");
        Optional<UserRegistration> userOpt = userRegistrationRepo.findByEmail(email);
            UserRegistration user = userOpt.get();
            todoItem.setUserRegistration(user);
            user.getTodoItems().add(todoItem);
            userRegistrationRepo.save(user);
        todoRepo.save(todoItem);
    }
}
