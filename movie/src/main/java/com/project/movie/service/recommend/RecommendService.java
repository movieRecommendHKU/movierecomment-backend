package com.project.movie.service.recommend;

import com.project.movie.domain.enums.RecommenderEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecommendService {
    List<Integer> getAllMovies(Integer userId);

    List<Integer> getMoviesByPolicy(Integer userId, RecommenderEnum policy);

    Boolean insetRecommendLog(Integer userId, String log);

    List<Integer> getUserRecommendLog(Integer userId);

}
