package com.project.movie.service.movie.impl;

import com.project.movie.domain.DO.Rating;
import com.project.movie.mapper.movie.RatingMapper;
import com.project.movie.service.movie.RatingService;
import jakarta.annotation.Resource;

import java.util.List;

public class RatingServiceImpl implements RatingService {

    @Resource
    RatingMapper ratingMapper;

    @Override
    public Rating rate(Rating rating) {
        ratingMapper.rate(rating);
        return rating;
    }

    @Override
    public boolean changeRating(Rating rating) {
        return 1 == ratingMapper.changeRating(rating);
    }

    @Override
    public double getMovieRatingByUser(Integer userId, Integer movieId) {
        return ratingMapper.getMovieRatingByUser(userId, movieId);
    }

    @Override
    public double getMovieAvgRating(Integer movieId) {
        return ratingMapper.getMovieAvgRating(movieId);
    }

    @Override
    public List<Rating> getAllMovieAvgRating() {
        return ratingMapper.getAllMovieAvgRating();
    }
}
