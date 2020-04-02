package com.angular.todos.service;
import com.angular.todos.model.Todo;
import java.util.List;

public interface TodoService {

    Todo addTodo(Todo todo);
    void deleteTodo(long id);
    Todo getById(long id);
    Iterable<Todo> search(String keyword);
    List<Todo> getAll();

}
