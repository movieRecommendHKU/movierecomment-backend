package com.project.movie.service.movie.action;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.domain.enums.UserMovieAction;
import com.project.movie.mapper.account.AccountMapper;
import com.project.movie.mapper.movie.MovieMapper;
import com.project.movie.service.account.AccountService;
import com.project.movie.service.movie.base.MovieService;
import com.project.movie.service.movie.kg.GraphService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
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

//    private User test;

    @Test
    public void init() {
//        test = accountService.register(new UserLoginVO().setUserName("TEST").setEmail("test@qq.com").setPassword("test"));
//        accountMapper.login(test);
        User user = accountMapper.findByEmail("test@qq.com");
        graphService.insertUser(user);
    }

    @Test
    public void collect() {
        User user = accountMapper.findByEmail("test@qq.com");
        List<MovieVO> movies = movieService.getMovieCompleteList(10, 5, "").getList();
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

    public void removeCollect(Integer collectId) {

    }

    public void getCollectionsByUser(Integer page, Integer pageSize, Integer userId) {

    }
}
