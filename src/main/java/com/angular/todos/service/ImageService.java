package com.angular.todos.service;

import com.angular.todos.model.Image;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;





public interface ImageService {
    byte [] getImage(String imageName);
    Image addImage(MultipartFile multipartFile) throws IOException;
}
