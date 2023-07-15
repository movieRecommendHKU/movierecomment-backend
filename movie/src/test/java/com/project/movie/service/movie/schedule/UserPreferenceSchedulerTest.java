package com.project.movie.service.movie.schedule;

import com.project.movie.schedule.UserPreferenceScheduler;
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
