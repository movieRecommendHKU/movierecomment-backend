package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.DO.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingMapper {
    Integer rate(Rating rating);

    Integer changeRating(Rating rating);

    Double getUserMovieRating(Integer userId, Integer movieId);
}
