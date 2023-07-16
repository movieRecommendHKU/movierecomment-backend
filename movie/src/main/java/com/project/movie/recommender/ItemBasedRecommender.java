package com.project.movie.recommender;

import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.DTO.MovieRecommend;
import com.project.movie.domain.enums.RecommenderEnum;
import com.project.movie.service.movie.action.CollectService;
import com.project.movie.service.movie.action.DislikeService;
import com.project.movie.service.movie.base.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ItemBasedRecommender extends AbsRecommender {

    private static final int ITEM_BASED_RECALL_MOVIE_LIMIT = 100;

    private static final double MOVIE_SIMILARITY_THRESHOLD = 0.5;

    @Autowired
    CollectService collectService;

    @Autowired
    MovieService movieService;

    @Autowired
    DislikeService dislikeService;

    @Override
    protected void register() {
        RECOMMENDERS.put(RecommenderEnum.ITEM_BASED, this);
    }

    @Override
    protected List<MovieRecommend> recall(Integer userId) {
        List<Integer> collections = collectService.getRecentCollectionsByUser(userId)
                .stream().map(Collect::getMovieId).toList();
        List<MovieRecommend> recallResult = collections.stream()
                .flatMap(collectMovieId -> movieService.getSimilarMovies(collectMovieId).stream())
                .map(movieSimilarity -> new MovieRecommend()
                        .setMovieId(movieSimilarity.getSimilarId())
                        .setCount(1)
                        .setWeight(movieSimilarity.getSimilarity()))
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
        List<Integer> sortResult = filterResult.stream()
                .sorted(Comparator.comparing(
                                (MovieRecommend movie) -> movie.getWeight() > MOVIE_SIMILARITY_THRESHOLD ? movie.getCount() : 0,
                                Comparator.reverseOrder())
                        .thenComparing(MovieRecommend::getWeight, Comparator.reverseOrder())
                )
                .map(MovieRecommend::getMovieId)
                .limit(ITEM_BASED_RECALL_MOVIE_LIMIT)
                .toList();
        return sortResult;
    }

}
