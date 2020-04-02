package com.angular.todos.endpoint;

import com.angular.todos.model.Image;
import com.angular.todos.model.Todo;
import com.angular.todos.service.ImageService;
import com.angular.todos.service.TodoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*")
public class TodoEndPoint {


    private final TodoService todoService;
    private final ImageService imageService;

    public TodoEndPoint(TodoService todoService, ImageService imageService) {
        this.todoService = todoService;
        this.imageService = imageService;
    }

    @PostMapping("/")
    public ResponseEntity addTodo(@RequestBody Todo todo){
        return ResponseEntity.ok(todoService.addTodo(todo));
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte [] getImage(@RequestParam("imageUrl") String imageUrl){
        return imageService.getImage(imageUrl);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable("id") long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{todoId}")
    public ResponseEntity addImage(@RequestParam(value = "file")MultipartFile file,@PathVariable("todoId") long todoId){
        try {
            Todo todo = todoService.getById(todoId);
            Image image = imageService.addImage(file);
            todo.setImage(image);
            todoService.addTodo(todo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public Iterable<Todo> search(@RequestParam("keyword") String keyword){
        Iterable<Todo> search = todoService.search(keyword);
        return search;
    }

    @GetMapping("/all")
    public List<Todo> getAll(){
        return todoService.getAll();
    }

}
