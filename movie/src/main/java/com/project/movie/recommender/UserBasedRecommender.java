package com.project.movie.recommender;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.DTO.UserSimilarity;
import com.project.movie.domain.DTO.MovieRecommend;
import com.project.movie.domain.enums.RecommenderEnum;
import com.project.movie.service.movie.action.CollectService;
import com.project.movie.service.movie.action.DislikeService;
import com.project.movie.service.search.SearchSimilarUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserBasedRecommender extends AbsRecommender {
    private static final int USER_BASED_RECALL_MOVIE_LIMIT = 100;

    private static final int SIMILAR_USER_LIMIT = 20;

//	private static final double USER_SIMILARITY_THRESHOLD = 0.6;


    @Autowired
    CollectService collectService;

    @Autowired
    DislikeService dislikeService;

    @Autowired
    SearchSimilarUserService searchSimilarUserService;

    @Override
    protected void register() {
        RECOMMENDERS.put(RecommenderEnum.USER_BASED, this);
    }

    @Override
    protected List<MovieRecommend> recall(Integer userId) {
        // filter similarity <= 0.6, done in sql
        List<UserSimilarity> similarUsers = searchSimilarUserService.searchByUserSimilarity(userId, SIMILAR_USER_LIMIT);

        // 20 similar users recent collected movies
        List<MovieRecommend> recallResult = similarUsers.stream()
                .flatMap(user -> collectService.getRecentCollectionsByUser(user.getUserId()).stream()
                        .map(collect -> {
                            return new MovieRecommend()
                                    .setMovieId(collect.getMovieId())
                                    .setCount(1)
                                    .setWeight(user.getSimilarity());
                        }))
                .toList();
        return recallResult;
    }

    @Override
    protected List<MovieRecommend> filter(List<MovieRecommend> recallResult, Integer userId) {
        List<Integer> dislikes = dislikeService.getUserDislikes(userId).stream().map(Dislike::getMovieId).toList();
        List<MovieRecommend> filterResult = recallResult.stream()
                .collect(Collectors.groupingBy(MovieRecommend::getMovieId))
                .values().stream()
                .map(movieRecommends -> {
                    double averageWeight = movieRecommends.stream()
                            .mapToDouble(MovieRecommend::getWeight)
                            .average()
                            .orElse(0.0); // 如果没有元素，则默认平均值为0.0
                    MovieRecommend mergedMovieRecommend = movieRecommends.get(0);
                    mergedMovieRecommend.setWeight(averageWeight);
                    mergedMovieRecommend.setCount(movieRecommends.size()); // 设置相同movieId的RecommendMovie个数
                    return mergedMovieRecommend;
                })
                .filter(movie -> !dislikes.contains(movie.getMovieId()))
                .distinct()
                .toList();
        return filterResult;
    }

    @Override
    protected List<Integer> sort(List<MovieRecommend> filterResult) {
        List<Integer> sortedResult = filterResult.stream()
                .sorted(Comparator
                        .comparingDouble((MovieRecommend movie) -> movie.getCount() * movie.getWeight())
                        .reversed())
                .map(MovieRecommend::getMovieId)
                .limit(USER_BASED_RECALL_MOVIE_LIMIT)
                .toList();
        return sortedResult;
    }
}
