package com.project.movie.recommender;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.MovieRecommend;
import com.project.movie.domain.enums.RecommenderEnum;
import com.project.movie.service.account.PreferenceService;
import com.project.movie.service.movie.action.DislikeService;
import com.project.movie.service.movie.kg.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PreferenceRecommender extends AbsRecommender {
    @Autowired
    GraphService graphService;

    @Autowired
    PreferenceService preferenceService;

    @Autowired
    DislikeService dislikeService;

    private static final int EACH_GENRE_RECALL_MOVIE_LIMIT = 20;

    private static final int GENRE_RECALL_MOVIE_LIMIT = 100;

    @Override
    protected void register() {
        RECOMMENDERS.put(RecommenderEnum.PREFERENCE, this);
    }

    @Override
    protected List<MovieRecommend> recall(Integer userId) {
        List<Genre> settingPreference = preferenceService.getUserPreference(userId);
        List<Genre> graphPreference = graphService.getUserPreferenceGenre(userId);

        // TODO: setting uses graphService ?
        List<MovieRecommend> settingRecommend = settingPreference.stream()
                .flatMap(genre -> graphService.getMoviesByGenre(genre, EACH_GENRE_RECALL_MOVIE_LIMIT).stream())
                .map(movieId -> new MovieRecommend()
                        .setMovieId(movieId)
                        .setCount(1)
                        .setWeight(1.))
                .toList();
        List<MovieRecommend> graphRecommend = graphPreference.stream()
                .flatMap(genre -> graphService.getMoviesByGenre(genre, EACH_GENRE_RECALL_MOVIE_LIMIT).stream())
                .map(movieId -> new MovieRecommend()
                        .setMovieId(movieId)
                        .setCount(1)
                        .setWeight(0.))
                .toList();
        List<MovieRecommend> recallResult = new ArrayList<>();
        recallResult.addAll(settingRecommend);
        recallResult.addAll(graphRecommend);
        return recallResult;
    }

    @Override
    protected List<MovieRecommend> filter(List<MovieRecommend> recallResult, Integer userId) {
        List<Integer> dislikes = dislikeService.getUserDislikes(userId).stream().map(Dislike::getMovieId).toList();
        List<MovieRecommend> filterResult = recallResult.stream()
                .collect(Collectors.groupingBy(MovieRecommend::getMovieId, Collectors.toList()))
                .values().stream()
                .map(movieRecommends -> movieRecommends.stream()
                        .reduce((movieRecommend, m) -> movieRecommend
                                .setCount(movieRecommend.getCount() + m.getCount())
                                .setWeight(Math.max(movieRecommend.getWeight(), m.getWeight())))
                        .orElse(null))
                .filter(Objects::nonNull)
                .filter(movie -> !dislikes.contains(movie.getMovieId()))
                .limit(GENRE_RECALL_MOVIE_LIMIT)
                .toList();
        return filterResult;
    }

    @Override
    protected List<Integer> sort(List<MovieRecommend> filterResult) {
        List<Integer> movies = filterResult.stream()
                .sorted(Comparator.comparing(MovieRecommend::getCount).reversed())
                .sorted(Comparator.comparing(MovieRecommend::getWeight).reversed())
                .map(MovieRecommend::getMovieId)
                .toList();
        return movies;
    }
}
