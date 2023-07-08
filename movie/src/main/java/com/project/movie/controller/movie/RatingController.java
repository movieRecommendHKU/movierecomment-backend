package com.project.movie.controller.movie;

import com.project.movie.domain.DO.Rating;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class RatingController {
    @Autowired
    RatingService ratingService;

    @PostMapping("/rate")
    public BaseResponse rate(@RequestBody Rating rating) {
        boolean hasRated = null == ratingService.getUserMovieRating(rating.getUserId(), rating.getMovieId());
        boolean res;
        if (hasRated) res = ratingService.changeRating(rating);
        else res = ratingService.rate(rating);
        return res ?
                new BaseResponse().setStatus(true).setContent("Rate successfully.") :
                new BaseResponse().setStatus(false).setContent("Rate failed.");
    }

    @PostMapping("/get_user_movie_rating")
    public BaseResponse getUserMovieRating(@RequestBody Rating rating) {
        Double rate = ratingService.getUserMovieRating(rating.getUserId(), rating.getMovieId());

        return rate == null ?
                new BaseResponse().setStatus(true).setContent("Rate successfully.") :
                new BaseResponse().setStatus(false).setContent("Rate failed.");
    }
}
