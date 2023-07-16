package com.project.movie.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserPreferenceSchedulerTest {
    @Autowired
    UserPreferenceScheduler userPreferenceScheduler;

    @Test
    public void updateUserPreference() {
        userPreferenceScheduler.updateUserPreference();
    }


}
