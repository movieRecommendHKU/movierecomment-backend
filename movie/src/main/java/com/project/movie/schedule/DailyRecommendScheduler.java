package com.project.movie.schedule;

import com.project.movie.domain.DO.User;
import com.project.movie.service.account.AccountService;
import com.project.movie.service.recommend.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class DailyRecommendScheduler {
    @Autowired
    AccountService accountService;

    @Autowired
    RecommendService recommendService;

    /**
     * Every Day 4am
     */
    @Scheduled(cron = "0 0 4 * ? ?")
    public void updateRecommendLog() {
        List<User> users = accountService.getUsersByLastLogin(getLastDate());
        log.info("DailyRecommendScheduler: update {} user.", users.size());
        for (User user : users) {
            Integer userId = user.getUserId();
            log.info("DailyRecommendScheduler: update user userId = {}", userId);
            List<Integer> recommendations = recommendService.getAllMovies(userId);
            String[] recommendationsArray = recommendations.stream().map(Object::toString).toArray(String[]::new);
            String recommendLog = String.join(",", recommendationsArray);
            boolean isLogged = recommendService.insetRecommendLog(userId, recommendLog);
            log.info("DailyRecommendScheduler: log successful = {}, recommendations = {}", isLogged, recommendLog);
        }
    }

    private Date getLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

}
