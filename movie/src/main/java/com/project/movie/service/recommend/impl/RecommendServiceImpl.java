package com.project.movie.service.recommend.impl;

import com.project.movie.domain.DO.Movie;
import com.project.movie.recommender.AbsRecommender;
import com.project.movie.service.recommend.RecommendService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Override
    public List<Integer> getAllMovies(Integer userId) {
        List<List<Integer>> recommendLists = new ArrayList<>();
        for (AbsRecommender recommender : AbsRecommender.getRecommenders())
            recommendLists.add(AbsRecommender.recommend(userId, recommender));
        List<Integer> recommendList = AbsRecommender.resort(recommendLists);
        return recommendList;
    }

    @Override
    public List<Integer> getMoviesByPolicy(Integer userId, String policy) {
        return null;
    }
}
