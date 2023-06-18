package com.project.movie.service.movie;

import com.project.movie.domain.DO.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    Rating rate(Rating rating);

    boolean changeRating(Rating rating);

    double getMovieRatingByUser(Integer userId, Integer movieId);

    double getMovieAvgRating(Integer movieId);

    List<Rating> getAllMovieAvgRating();
}
