package com.angular.todos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String imagePath;


    private static Map<Long,Image> allImages = new HashMap<>();
    private static long countId = 0;

    public Image(String imagePath){
        if (allImages == null){
            allImages = new HashMap<>();
        }

        if (imagePath != null && !imagePath.isEmpty()){
            this.imagePath = imagePath;
        }

        if (!hasImage()){
            countId++;
            this.id = countId;
            allImages.put(id,this);
        }
    }

    private boolean hasImage() {
        for (Image value: allImages.values()){
            if (value.equals(this) && value.hashCode() == this.hashCode()){
                return true;
            }
        }
        return false;
    }

public static List<Image> getAllImages(){
        return new LinkedList<>(allImages.values());
}
}




