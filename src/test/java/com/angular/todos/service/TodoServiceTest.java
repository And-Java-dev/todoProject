package com.angular.todos.service;


import com.angular.todos.model.Image;
import com.angular.todos.model.Todo;
import com.angular.todos.repository.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @MockBean
    private TodoRepository todoRepository;


    @Test
    public void findTodoByIdTest() {
        long id = 30;

        Todo todo = new Todo("O", true, new Date(), new Image("989"));

        when(todoRepository.getOne(id)).thenReturn(todo);
        Todo byId = todoService.getById(id);
        assertNotNull(byId);
        assertEquals(todo.getTitle(), byId.getTitle());

    }

    @Test
    public void findAllTodosTest() {
        Todo todo = new Todo("O", true, new Date(), new Image("989"));
        Todo todo1 = new Todo("Osd", true, new Date(), new Image("989"));
        Todo todo2 = new Todo("O", true, new Date(), new Image("989"));
        List<Todo> expected = Todo.getAllTodo();
        when(todoRepository.findAll()).thenReturn(expected);
        List<Todo> all = todoService.getAll();
        assertFalse(all.isEmpty());
        assertEquals(expected, all);

    }

    @Test
    public void addTodoTest() {
        Todo todo2 = new Todo("O", true, new Date(), new Image("989"));
        when(todoRepository.save(todo2)).thenReturn(todo2);
        Todo todo = todoService.addTodo(todo2);
        assertNotNull(todo);
        assertEquals(todo2, todo);

    }

    @Test
    public void deletTodoByIdTest() {
        long id = 60;
        todoService.deleteTodo(id);
        verify(todoRepository).deleteById(id);
    }

    @Test
    public void searchTodoByKeyword() {
        Todo todo1 = new Todo("O21", true, new Date(), new Image("989"));
        List<Todo> expected = Todo.getAllTodo();
        when(todoService.search("2")).thenReturn(expected);
        Iterable<Todo> search = todoService.search("2");
        assertNotNull(search);
        assertEquals(expected,search);
    }
}
