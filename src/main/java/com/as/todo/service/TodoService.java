package com.as.todo.service;

import com.as.todo.entity.TodoItem;
import com.as.todo.entity.UserRegistration;
import com.as.todo.payload.response.MessageResponse;
import com.as.todo.repository.TodoRepo;
import com.as.todo.repository.UserRegistrationRepo;
import com.as.todo.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private UserRegistrationRepo userRegistrationRepo;

    public MessageResponse createTodo(TodoItem todoItem, String email) {
        if(todoItem.getCategory() == null || todoItem.getCategory().equals(""))
            todoItem.setCategory("Default");

        Optional<UserRegistration> userOpt = userRegistrationRepo.findByEmail(email);
        if(userOpt.isPresent()) {
            todoItem.setStatus(Status.valueOf(todoItem.getStatus().toString().toUpperCase()));
            UserRegistration user = userOpt.get();
            user.getTodoItems().add(todoItem);
            userRegistrationRepo.save(user);
            return new MessageResponse("ToDo Created.");
        } else return new MessageResponse("Email does not exists.");
    }

    public MessageResponse updateTodo(TodoItem todoItem, String email) {
        Optional<UserRegistration> userOpt = userRegistrationRepo.findByEmail(email);
        if(userOpt.isPresent()) {
            List<TodoItem> todosOfUser = userOpt.get().getTodoItems();
            Optional<TodoItem> item = userOpt.get().getTodoItems().stream()
                    .filter(todo -> todo.getId().equals(todoItem.getId())).findAny();
            if(item.isPresent()) {
                if (todoItem.getCategory() == null) {
                    todoItem.setCategory("Default");
                }

                todoItem.setCreatedDate(item.get().getCreatedDate());
                todoItem.setUpdateDate(new Date());
                todosOfUser.remove(item.get());
                todosOfUser.add(todoItem);
                userOpt.get().setTodoItems(todosOfUser);
                userRegistrationRepo.save(userOpt.get());
            }
        }
        return new MessageResponse("ToDo Updated.");

    }

    public void modifyCategory(String category, Long id) {
        Optional<TodoItem> todoItem = todoRepo.findById(id);
        if(todoItem.isPresent() && !(todoItem.get().getCategory().equals(category))){
            todoItem.get().setUpdateDate(new Date());
            todoItem.get().setCategory(category);
            todoRepo.save(todoItem.get());
        }
    }

    public List<TodoItem> todosByUser(String email, String filterType, String filterValue) {
        Optional<UserRegistration> userRegistration = userRegistrationRepo.findByEmail(email);
        if(userRegistration.isPresent()) {
            if("category".equalsIgnoreCase(filterType)) {
                return userRegistration.get().getTodoItems().stream()
                        .filter(todoItem -> todoItem.getCategory().equalsIgnoreCase(filterValue))
                        .collect(Collectors.toList());
            } else if("status".equalsIgnoreCase(filterType)) {
                try{
                    Status s = Status.valueOf(filterValue.toUpperCase());
                    return userRegistration.get().getTodoItems().stream()
                            .filter(todoItem -> todoItem.getStatus().equals(s))
                            .collect(Collectors.toList());
                } catch (IllegalArgumentException e) {
                    return new ArrayList<>();
                }
            }else
                return userRegistration.get().getTodoItems();
        }
        return new ArrayList<>();
    }
}
