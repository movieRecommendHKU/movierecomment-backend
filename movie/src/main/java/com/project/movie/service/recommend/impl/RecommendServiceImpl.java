package com.project.movie.service.recommend.impl;

import com.project.movie.domain.enums.RecommenderEnum;
import com.project.movie.mapper.recommend.RecommendMapper;
import com.project.movie.recommender.AbsRecommender;
import com.project.movie.service.recommend.RecommendService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    RecommendMapper recommendMapper;

    @Override
    public List<Integer> getAllMovies(Integer userId) {
        List<List<Integer>> recommendLists = new ArrayList<>();
        for (AbsRecommender recommender : AbsRecommender.getRecommenders())
            recommendLists.add(AbsRecommender.recommend(userId, recommender));
        List<Integer> recommendList = AbsRecommender.resort(recommendLists);
        return recommendList;
    }

    @Override
    public List<Integer> getMoviesByPolicy(Integer userId, RecommenderEnum policy) {
        AbsRecommender recommender = AbsRecommender.getRecommenderByProtocol(policy);
        List<Integer> ids = AbsRecommender.recommend(userId, recommender);
        return ids;
    }

    @Override
    public Boolean insetRecommendLog(Integer userId, String log) {
        return 1 == recommendMapper.insertRecommendLog(userId, log);
    }
}
