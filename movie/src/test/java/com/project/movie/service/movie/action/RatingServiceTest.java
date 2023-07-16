package com.project.movie.service.movie.action;

import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Rating;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.domain.enums.UserMovieAction;
import com.project.movie.mapper.account.AccountMapper;
import com.project.movie.service.account.AccountService;
import com.project.movie.service.movie.base.MovieService;
import com.project.movie.service.movie.kg.GraphService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class RatingServiceTest {
    @Autowired
    RatingService ratingService;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AccountService accountService;

    @Autowired
    MovieService movieService;

    @Autowired
    GraphService graphService;

    private static Random random = new Random();

    @Test
    public void rating() {
        User user = accountMapper.findByEmail("celiaf@qq.com");
        List<MovieVO> movies = movieService.getMovieCompleteList(2, 20, "").getList();
        List<Rating> ratings = movies.stream()
                .map(movie -> new Rating()
                        .setMovieId(movie.getMovieId())
                        .setUserId(user.getUserId()).setRating(Math.round(random.nextDouble(7)) + 3.))
                .toList();
        System.out.println(ratings);
        ratings.forEach(rating -> {
            ratingService.rate(rating);
            String action = GraphService.getRateRelName(rating.getRating());
            if (action != null) {
                graphService.takeAction(user.getUserId(), rating.getMovieId(), action);
            }
        });
    }

}
