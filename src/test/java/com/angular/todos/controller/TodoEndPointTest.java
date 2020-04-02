package com.angular.todos.controller;


import com.angular.todos.model.Image;
import com.angular.todos.model.Todo;
import com.angular.todos.service.ImageService;
import com.angular.todos.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.mock.web.MockRequestDispatcher.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoEndPointTest {
    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @MockBean
    private TodoService todoService;

    @MockBean
    private ImageService imageService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void searchTodoByKeywordTest() throws Exception {
        Todo todo = new Todo("KkK", false, new Date(), new Image("Images"));
        Todo todo1 = new Todo("zzz", false, new Date(), new Image("Images"));
        Todo todo2 = new Todo("ak147", false, new Date(), new Image("Images"));
        List<Todo> expected = new LinkedList<>();
        expected.add(todo);
        expected.add(todo1);
        expected.add(todo2);
        when(todoService.search("K")).thenReturn(expected);
        mvc.perform(get("/todos/search?keyword=" + "K"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void addTodoImageTest() throws Exception {
        long id = 15;
        FileInputStream fis = new FileInputStream("src/ImageUploadDir/1585298304577 . . images.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("file","1585298304577 . . images.jpg",MediaType.IMAGE_JPEG_VALUE,
                fis);
        Image image1 = new Image("1585298304577.png");
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/todos/" + id);
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        Todo todo = new Todo("KkK", false, new Date(),image1);
        when(todoService.getById(id)).thenReturn(todo);
        mvc.perform(builder
                .file(multipartFile))
                .andDo(print())
                .andExpect(status().isOk());
        todo.setImage(image1);
        todoService.addTodo(todo);
    }

    @Test
    public void getAllTodosTest() throws Exception {
        Todo todo = new Todo("KkK", false, new Date(), new Image("Images"));
        Todo todo1 = new Todo("zzz", false, new Date(), new Image("Images"));
        Todo todo2 = new Todo("ak147", false, new Date(), new Image("Images"));
        List<Todo> expected = new LinkedList<>();
        expected.add(todo);
        expected.add(todo1);
        expected.add(todo2);
        when(todoService.getAll()).thenReturn(expected);
        mvc.perform(get("/todos/all"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void createTodoTest() throws Exception {
        Todo todo = new Todo("KkK", false, new Date(), new Image("Images"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(todo);
        when(todoService.addTodo(todo)).thenReturn(todo);
        mvc.perform(post("/todos/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTodoByIdTest() throws Exception {
        long id = 7;
        mvc.perform(delete("/todos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getImageTest() throws Exception {
        String imageName = " . . image.jpg";
        InputStream in = new FileInputStream(imageUploadDir + imageName);
        byte[] bytes = IOUtils.toByteArray(in);
        when(imageService.getImage(imageName)).thenReturn(bytes);
        mvc.perform(get("/todos/")
        .param("imageUrl",imageName)
        .contentType(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(status().isOk())
                .andDo(print());

    }
}
