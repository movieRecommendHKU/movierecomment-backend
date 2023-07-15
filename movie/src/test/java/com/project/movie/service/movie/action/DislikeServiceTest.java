package com.project.movie.service.movie.action;

import com.project.movie.domain.DO.Dislike;
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
@TestPropertySource("classpath:application.yml")
public class DislikeServiceTest {
    @Autowired
    DislikeService dislikeService;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AccountService accountService;

    @Autowired
    MovieService movieService;

    @Autowired
    GraphService graphService;


    @Test
    public void dislike() {
        User user = accountMapper.findByEmail("test@qq.com");
        List<MovieVO> movies = movieService.getMovieCompleteList(3, 10, "").getList();
        List<Dislike> dislikes = movies.stream()
                .map(movie -> new Dislike()
                        .setMovieId(movie.getMovieId())
                        .setUserId(user.getUserId()))
                .toList();
        dislikes.forEach(dislike -> {
            dislikeService.dislike(dislike);
            graphService.takeAction(user.getUserId(), dislike.getMovieId(), UserMovieAction.dislike.name());
        });
    }

}
