package com.project.movie.service.movie.action;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
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

import java.util.List;

@SpringBootTest
public class CollectServiceTest {
    @Autowired
    CollectService collectService;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AccountService accountService;

    @Autowired
    MovieService movieService;

    @Autowired
    GraphService graphService;

    @Test
    public void collect() {
        User user = accountMapper.findByEmail("celiaf@qq.com");
        List<MovieVO> movies = movieService.getMovieCompleteList(1, 1, "").getList();
        List<Collect> collects = movies.stream()
                .map(movie -> new Collect()
                        .setMovieId(movie.getMovieId())
                        .setUserId(user.getUserId()))
                .toList();
        System.out.println(collects);
        collects.forEach(collect -> {
            collectService.collect(collect);
            graphService.takeAction(user.getUserId(), collect.getMovieId(), UserMovieAction.collect.name());
        });
    }
}
