package com.project.movie.service.movie.kg;

import com.project.movie.domain.DO.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
public class GraphServiceTest {
    @Autowired
    GraphService graphService;

    @Test
    @Rollback
    public void getUserPreferenceTest() {
        graphService.insertUser(new User().setUserId(2).setUserName("test"));
        List<String> labels = graphService.getUserPreference(3);
        Assertions.assertEquals(labels.size(), 0);
    }
}
