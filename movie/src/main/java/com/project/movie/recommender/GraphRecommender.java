package com.project.movie.recommender;

import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.MovieRecommend;
import com.project.movie.domain.enums.RecommenderEnum;
import com.project.movie.service.account.PreferenceService;
import com.project.movie.service.movie.action.CollectService;
import com.project.movie.service.movie.action.DislikeService;
import com.project.movie.service.movie.kg.GraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GraphRecommender extends AbsRecommender {
    @Autowired
    GraphService graphService;

    @Autowired
    CollectService collectService;

    @Autowired
    DislikeService dislikeService;

    private static final int COLLECTION_MOVIE_LIMIT = 20;

    private static final int EACH_GRAPH_ROUTE_MOVIE_LIMIT = 20;

    private static final int GRAPH_SEARCH_DEPTH = 2;

    private static final int GRAPH_RECALL_MOVIE_LIMIT = 100;

    @Override
    protected void register() {
        RECOMMENDERS.put(RecommenderEnum.KG_BASED, this);
    }

    @Override
    protected List<MovieRecommend> recall(Integer userId) {
        List<Integer> collections = collectService
                .getCollectionsByUser(1, COLLECTION_MOVIE_LIMIT, "collectId desc", userId)
                .getList().stream().map(Collect::getMovieId).toList();

        List<MovieRecommend> recallResult = collections.stream()
                .flatMap(startMovie -> graphService
                        .getMoviesInDepth(startMovie, GRAPH_SEARCH_DEPTH, EACH_GRAPH_ROUTE_MOVIE_LIMIT)
                        .stream())
                .map(movieId -> new MovieRecommend().setMovieId(movieId).setCount(1).setWeight(1.))
                .toList();
        log.info("recall: {}", recallResult);
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
                                .setWeight(1.))
                        .orElse(null))
                .filter(Objects::nonNull)
                .filter(movie -> !dislikes.contains(movie.getMovieId()))
                .limit(GRAPH_RECALL_MOVIE_LIMIT)
                .toList();
        log.info("filter: {}", filterResult);
        return filterResult;
    }

    @Override
    protected List<Integer> sort(List<MovieRecommend> filterResult) {
        List<Integer> movies = filterResult.stream()
                .sorted(Comparator.comparing(MovieRecommend::getCount).reversed())
                .map(MovieRecommend::getMovieId)
                .toList();
        log.info("sort: {}", movies);
        return movies;
    }
}
