package com.project.movie.service.schedule;

import com.project.movie.domain.DO.User;
import com.project.movie.domain.DTO.GraphNode;
import com.project.movie.domain.enums.UserMovieAction;
import com.project.movie.mapper.account.AccountMapper;
import com.project.movie.service.movie.kg.GraphService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class UserPreferenceScheduler {
    @Resource
    AccountMapper accountMapper;

    @Autowired
    GraphService graphService;

    private static final Map<UserMovieAction, Double> ACTION_SCORE_MAP = new HashMap<>() {{
        this.put(UserMovieAction.collect, 2.0);
        this.put(UserMovieAction.high_rate, 1.5);
        this.put(UserMovieAction.low_rate, -0.5);
        this.put(UserMovieAction.dislike, -2.0);
    }};

    /**
     * Every Month 1st 1am
     */
    @Scheduled(cron = "0 0 1 1 * ?")
    public void updateUserPreference() {
        List<User> users = accountMapper.getUsersByLastLogin(getLastMonth());
        log.info("UserPreferenceScheduler: update {} user.", users.size());
        for (User user : users) {
            Integer userId = user.getUserId();
            log.info("UserPreferenceScheduler: update user userId = {}", userId);
            List<String> oldGenres = graphService.getUserPreference(userId);
            List<String> newGenres = getUserPreferenceGenres(userId);
            List<String> removeGenres = oldGenres.stream().filter(genre -> !newGenres.contains(genre)).toList();
            List<String> insertGenres = newGenres.stream().filter(genre -> !oldGenres.contains(genre)).toList();
            graphService.updateUserPreference(userId, removeGenres, insertGenres);
            log.info("original preference: {}", oldGenres);
            log.info("new preference: {}", newGenres);
            log.info("remove genres: {}", removeGenres);
            log.info("insert genres: {}", insertGenres);
        }
    }

    private Date getLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    private List<String> getUserPreferenceGenres(Integer userId) {
        Map<UserMovieAction, List<GraphNode>> actionMovieMap = graphService.getUserActionsToMovies(userId);
        Map<String, Double> genreScoreMap = getGenreScoreMap(actionMovieMap);
        List<Map.Entry<String, Double>> positiveGenreScores = genreScoreMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .filter((item) -> item.getValue() > 0)
                .toList();
        double avgPositiveScore = positiveGenreScores.stream()
                .mapToDouble(Map.Entry::getValue)
                .average()
                .orElse(0);
        List<Map.Entry<String, Double>> preferenceGenres = positiveGenreScores.stream()
                .filter((item) -> item.getValue() > avgPositiveScore)
                .toList();
        log.info("Preference with scores: {}", preferenceGenres);
        return preferenceGenres.stream().map(Map.Entry::getKey).toList();
    }

    private Map<String, Double> getGenreScoreMap(Map<UserMovieAction, List<GraphNode>> actionMovieMap) {
        Map<String, Double> genreScoreMap = new LinkedHashMap<>();
        for (UserMovieAction action : actionMovieMap.keySet()) {
            double actionScore = ACTION_SCORE_MAP.get(action);
            for (GraphNode movie : actionMovieMap.get(action)) {
                for (String genre : movie.getLabels()) {
                    double score = genreScoreMap.getOrDefault(genre, 0.);
                    genreScoreMap.put(genre, score + actionScore);
                }
            }
        }
        return genreScoreMap;
    }

}
