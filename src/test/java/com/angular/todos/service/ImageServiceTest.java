package com.angular.todos.service;


import com.angular.todos.model.Image;
import com.angular.todos.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @MockBean
    ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;

    @Test
    public void getImageTest() throws IOException {
        Image image = new Image("1585298304577 . . images.jpg");
        InputStream in = new FileInputStream(imageUploadDir + image.getImagePath());
        byte[] bytes = IOUtils.toByteArray(in);
        byte[] image1 = imageService.getImage(image.getImagePath());
        assertNotNull(image1);
        assertArrayEquals(bytes,image1);

    }


    @Test
    public void addImageTest() throws IOException {
        MockMultipartFile multipartFileToSend = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "some_image".getBytes());
        Image image1 = imageService.addImage(multipartFileToSend);
        String fileName = image1.getImagePath().substring(0,13) + " . . " + multipartFileToSend.getOriginalFilename();
        File file = new File(imageUploadDir,fileName);
        multipartFileToSend.transferTo(file);
        Image image = new Image(fileName);
        Optional<Image> img = Optional.ofNullable(image1);
        when(imageRepository.save(image)).thenReturn(image);
        assertTrue(img.isPresent());
        assertEquals(image.getImagePath(),image1.getImagePath());

    }

}
