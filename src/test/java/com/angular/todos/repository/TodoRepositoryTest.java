package com.angular.todos.repository;


import com.angular.todos.model.Image;
import com.angular.todos.model.Todo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:data_test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepositoryMock;

    @Mock
    private TodoRepository mock;


    @Test
    public void findTodoByIdTest() {
        Image image = new Image("asasasas");
        Todo todo = todoRepositoryMock.save(Todo.builder()
                .title("A")
                .completed(false)
                .date(new Date())
                .image(image)
                .build()
        );
        Todo byId = todoRepositoryMock.getOne(todo.getId());
        assertNotNull(byId);
        assertEquals("A", byId.getTitle());
    }

    @Test
    public void findAllTodoTest() {
        Image image = new Image("asasasas");
        Todo todo2 = new Todo("B", true, new Date(), image);
        Todo todo3 = new Todo("C", true, new Date(), image);
        Todo todo4 = new Todo("D", true, new Date(), image);

        List<Todo> expected = Todo.getAllTodo();
        List<Todo> actual = todoRepositoryMock.findAll();
        actual.add(todo2);
        actual.add(todo3);
        actual.add(todo4);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void deletTodoTest() {
      long id = 30;
      mock.deleteById(id);
        verify(mock).deleteById(id);

    }

    @Test
    public void saveTodoTest() {
        Image image = new Image("asasasas");
        Todo todo = Todo.builder()
                .title("A")
                .completed(false)
                .date(new Date())
                .image(image)
                .build();
        Todo save = todoRepositoryMock.save(todo);
        assertEquals(todo,save);
    }

}
