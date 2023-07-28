package com.project.movie.schedule;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.DTO.GenreScore;
import com.project.movie.domain.DTO.GraphNode;
import com.project.movie.domain.DTO.UserSimilarityInfo;
import com.project.movie.domain.enums.UserMovieAction;
import com.project.movie.service.account.AccountService;
import com.project.movie.service.account.PreferenceService;
import com.project.movie.service.movie.kg.GraphService;
import com.project.movie.service.search.UserSearchDataService;
import com.project.movie.utils.GenreVectorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class UserPreferenceScheduler {
    @Autowired
    AccountService accountService;

    @Autowired
    PreferenceService preferenceService;

    @Autowired
    GraphService graphService;

    @Autowired
    GenreVectorUtil genreVectorUtil;

    @Autowired
    UserSearchDataService userSearchDataService;

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
        List<User> users = accountService.getUsersByLastLogin(getLastMonth());
        log.info("UserPreferenceScheduler: update {} user.", users.size());
        for (User user : users) {
            Integer userId = user.getUserId();
            log.info("UserPreferenceScheduler: update user userId = {}", userId);
            List<String> oldGenres = graphService.getUserPreferenceLabel(userId);
            List<GenreScore> genreScoreList = getGenreScoreList(userId);
            List<String> newGenres = getUserPreferenceGenres(genreScoreList);
            List<String> removeGenres = oldGenres.stream().filter(genre -> !newGenres.contains(genre)).toList();
            List<String> insertGenres = newGenres.stream().filter(genre -> !oldGenres.contains(genre)).toList();
            graphService.updateUserPreference(userId, removeGenres, insertGenres);
            log.info("original preference: {}", oldGenres);
            log.info("new preference: {}", newGenres);
            log.info("remove genres: {}", removeGenres);
            log.info("insert genres: {}", insertGenres);

            unionSettingPreference(userId, genreScoreList);
            genreVectorUtil.normalize(genreScoreList);
            log.info("After normalization: {}", genreScoreList);
            List<Double> preferenceVector = genreVectorUtil.convertGenreVector(genreScoreList);
            try {
                userSearchDataService.addUserToElasticSearch(new UserSimilarityInfo(userId, preferenceVector));
            } catch (Exception e) {
                log.error("updateUserPreference addUserToElasticSearch failed. user: {}, msg: {}", userId, e.getMessage());
            }
        }
    }

    private void unionSettingPreference(Integer userId, List<GenreScore> genreScoreList) {
        List<Genre> settingPreference = preferenceService.getUserPreference(userId);
        double maxScore = genreScoreList.stream().mapToDouble(GenreScore::getScore).max().orElse(0);
        List<Integer> miningGenreIds = genreScoreList.stream().map(GenreScore::getGenreId).toList();
        settingPreference.forEach(settingGenre -> {
            double score = maxScore;
            if (miningGenreIds.contains(settingGenre.getGenreId())) {
                genreScoreList.forEach(miningGenre -> {
                    if (miningGenre.getGenreId().intValue() == settingGenre.getGenreId().intValue()) {
                        miningGenre.setScore(miningGenre.getScore() + score);
                    }
                });
            } else {
                genreScoreList.add(new GenreScore()
                        .setGenreName(settingGenre.getGenreName())
                        .setScore(score));
            }
        });
    }


    private Date getLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    private List<String> getUserPreferenceGenres(List<GenreScore> genreScoreList) {

        List<GenreScore> positiveGenreScores = genreScoreList.stream()
                .sorted(Comparator.comparingDouble(GenreScore::getScore).reversed())
                .filter((genreScore) -> genreScore.getScore() > 0)
                .toList();
        double avgPositiveScore = positiveGenreScores.stream()
                .mapToDouble(GenreScore::getScore)
                .average()
                .orElse(0);
        List<GenreScore> preferenceGenres = positiveGenreScores.stream()
                .filter((genreScore) -> genreScore.getScore() > avgPositiveScore)
                .toList();
        log.info("Preference with scores: {}", preferenceGenres);
        return preferenceGenres.stream().map(GenreScore::getGenreName).toList();
    }

    private List<GenreScore> getGenreScoreList(Integer userId) {
        Map<UserMovieAction, List<GraphNode>> actionMovieMap = graphService.getUserActionsToMovies(userId);
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
        List<GenreScore> genreScoreList = genreScoreMap.entrySet().stream()
                .map(entry -> new GenreScore()
                        .setGenreName(entry.getKey())
                        .setScore(entry.getValue()))
                .toList();
        log.info("All genres with scores: {}", genreScoreList);
        return genreScoreList;
    }

}
