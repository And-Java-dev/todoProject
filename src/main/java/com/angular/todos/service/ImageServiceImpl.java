package com.angular.todos.service;

import com.angular.todos.model.Image;
import com.angular.todos.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageServiceImpl implements ImageService{
    @Value("${image.upload.dir}")
    private String imageUploadDir;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public byte[] getImage(String imageName) {
        InputStream in = null;
        try {
            in = new FileInputStream(imageUploadDir + imageName);
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Image addImage(MultipartFile multipartFile) throws IOException {
        String fileName = System.currentTimeMillis() + " . . " + multipartFile.getOriginalFilename();
        File uplploadFile = new File(imageUploadDir, fileName);
        multipartFile.transferTo(uplploadFile);
        Image image = Image.builder()
                .imagePath(fileName)
                .build();
        imageRepository.save(image);
        return image;
    }
}
