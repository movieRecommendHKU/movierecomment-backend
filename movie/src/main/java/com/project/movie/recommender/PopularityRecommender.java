package com.project.movie.recommender;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.DTO.MovieRecommend;
import com.project.movie.domain.enums.RecommenderEnum;
import com.project.movie.service.movie.action.DislikeService;
import com.project.movie.service.movie.base.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PopularityRecommender extends AbsRecommender {

    @Autowired
    MovieService movieService;

    @Autowired
    DislikeService dislikeService;

    private static final int POPULAR_MOVIE_LIMIT = 100;

    @Override
    protected void register() {
        RECOMMENDERS.put(RecommenderEnum.POPULARITY, this);
    }

    /**
     * @param userId
     * @return films in this year
     */
    @Override
    protected List<MovieRecommend> recall(Integer userId) {
        List<MovieRecommend> recallResult = movieService.getHotMoviesThisYear().stream()
                .map(movie -> {
                    return new MovieRecommend()
                            .setMovieId(movie.getMovieId())
                            .setWeight(movie.getPopularity())
                            .setCount(1);
                })
                .toList();
        return recallResult;
    }

    @Override
    protected List<MovieRecommend> filter(List<MovieRecommend> recallResult, Integer userId) {
        List<Integer> dislikes = dislikeService.getUserDislikes(userId).stream().map(Dislike::getMovieId).toList();
        List<MovieRecommend> filterResult = recallResult.stream()
                .filter(movie -> !dislikes.contains(movie.getMovieId()))
                .toList();
        return filterResult;
    }

    @Override
    protected List<Integer> sort(List<MovieRecommend> filterResult) {
        List<Integer> sortResult = filterResult.stream()
                .map(MovieRecommend::getMovieId)
                .limit(POPULAR_MOVIE_LIMIT)
                .toList();
        return sortResult;
    }
}
