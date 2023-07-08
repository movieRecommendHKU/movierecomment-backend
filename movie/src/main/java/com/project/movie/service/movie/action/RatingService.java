package com.project.movie.service.movie.action;

import com.project.movie.domain.DO.Rating;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {
    boolean rate(Rating rating);

    boolean changeRating(Rating rating);

    Double getUserMovieRating(Integer userId, Integer movieId);
}
