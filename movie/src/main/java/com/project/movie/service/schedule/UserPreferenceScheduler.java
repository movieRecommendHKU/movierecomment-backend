package com.project.movie.service.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserPreferenceScheduler {
    /**
     * Every Monday 2 a.m.
     */
    @Scheduled(cron = "0 0 2 ? * 2")
    public void updateUserPreference(){

    }
}
