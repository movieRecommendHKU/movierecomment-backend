package com.project.movie.service.movie.action.impl;

import com.project.movie.domain.DO.Rating;
import com.project.movie.mapper.movie.RatingMapper;
import com.project.movie.service.movie.action.RatingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
@Service
public class RatingServiceImpl implements RatingService {

    @Resource
    RatingMapper ratingMapper;

    @Override
    public boolean rate(Rating rating) {
        return null != ratingMapper.rate(rating);
    }

    @Override
    public boolean changeRating(Rating rating) {
        return 1 == ratingMapper.changeRating(rating);
    }

    @Override
    public Double getUserMovieRating(Integer userId, Integer movieId) {
        return ratingMapper.getUserMovieRating(userId, movieId);
    }
}
