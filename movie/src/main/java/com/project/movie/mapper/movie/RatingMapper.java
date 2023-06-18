package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.DO.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingMapper {
    int rate(Rating rating);

    int changeRating(Rating rating);

    double getMovieRatingByUser(Integer userId, Integer movieId);

    double getMovieAvgRating(Integer movieId);

    List<Rating> getAllMovieAvgRating();
}
