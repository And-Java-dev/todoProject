package com.angular.todos.model;

import com.sun.org.apache.bcel.internal.generic.I2F;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "todos")
public class Todo {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private boolean completed;

    @Column
    @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private Date date;

    @OneToOne
    private Image image;


    private static Map<Long,Todo> allTodo = new HashMap<>();
    private static long countId = 0;

    public Todo(String title,boolean completed, Date date,Image image){
        if (allTodo == null){
            allTodo = new HashMap<>();
        }

        if (title != null && !title.isEmpty() && date != null && image != null){
            this.title = title;
            this.date = date;
            this.image = image;
            this.completed = completed;
        }

        if (!hasTodo()){
            countId++;
            this.id = countId;
            allTodo.put(id,this);
        }
    }

    private boolean hasTodo(){
        for (Todo value : allTodo.values()) {
            if (value.equals(this) && value.hashCode() == this.hashCode() ){
                return true;
            }
        }
        return false;
    }

    public static List<Todo> getAllTodo() {
        return new LinkedList<>(allTodo.values());
    }



}
