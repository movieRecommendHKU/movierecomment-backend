package com.project.movie.service.service;

import com.project.movie.service.search.SearchSimilarUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SearchSimilarUserServiceTest {

    @Autowired
    SearchSimilarUserService searchSimilarUserService;

    @Test
    public void testGetUser() {
        try {
            List<Double> vector = searchSimilarUserService.getVectorByUserId(16);
            System.out.println(vector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
