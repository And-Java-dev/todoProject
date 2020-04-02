package com.angular.todos.repository;


import com.angular.todos.model.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:data_test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ImageRepositoryTest {

    @MockBean
    private ImageRepository imageRepository;


    @Test
    public void getAllImagesTest() {
        Image image = new Image("aaa");
        Image image1 = new Image("555");
        List<Image> expected = Image.getAllImages();
        List<Image> actual = new LinkedList<>();
        actual.add(image);
        actual.add(image1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getImagesByIdTest() {
        Image image = imageRepository.save(Image.builder().imagePath("a").build());
        Image byId = imageRepository.getOne(image.getId());
        assertNotNull(byId);
        Assert.assertEquals("a",image.getImagePath());

    }
}
