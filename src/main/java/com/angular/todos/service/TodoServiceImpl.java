package com.angular.todos.service;

import com.angular.todos.model.QTodo;
import com.angular.todos.model.Todo;
import com.angular.todos.repository.ImageRepository;
import com.angular.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    QTodo qTodo = QTodo.todo;
//    String pattern = "MM-dd-yyyy HH:mm:ss";
//    DateFormat df = new SimpleDateFormat(pattern);
//    String day = df.format(qTodo.date);


    @Override
    public Iterable<Todo> search(String keyword) {
        return todoRepository.findAll(qTodo.title.contains(keyword).or(qTodo.image.imagePath.contains(keyword)));
    }

    @Override
    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(long id) {
        todoRepository.deleteById(id);
    }



    @Override
    public Todo getById(long id) {
        return todoRepository.getOne(id);
    }


}
