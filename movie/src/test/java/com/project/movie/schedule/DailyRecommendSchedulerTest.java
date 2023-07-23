package com.project.movie.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DailyRecommendSchedulerTest {
    @Autowired
    DailyRecommendScheduler dailyRecommendScheduler;

    @Test
    public void updateUserPreference() {
        dailyRecommendScheduler.updateRecommendLog();
    }


}
