package com.project.movie.service.movie.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.mapper.cast.CastMapper;
import com.project.movie.mapper.director.DirectorMapper;
import com.project.movie.mapper.movie.MovieMapper;
import com.project.movie.mapper.producer.ProducerMapper;
import com.project.movie.service.movie.base.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieMapper movieMapper;

    @Autowired
    CastMapper castMapper;

    @Autowired
    DirectorMapper directorMapper;

    @Autowired
    ProducerMapper producerMapper;

    /*
        get movie list with cast and director list
     */
    @Override
    public PageInfo<MovieVO> getMovieCompleteList(int pageNum, int pageSize, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        try {
            List<Movie> moviesBaseInfo = movieMapper.getMovieList();
            List<MovieVO> movies = new ArrayList<>();
            for (Movie info : moviesBaseInfo) {
                int movieId = info.getMovieId();
                MovieVO vo = new MovieVO()
                        .setMovieId(movieId)
                        .setMovieName(info.getMovieName())
                        .setOverview(info.getOverview())
                        .setCasts(castMapper.getCastsByMovieId(movieId))
                        .setDirector(directorMapper.getDirectorByMovieId(movieId))
                        .setPopularity(info.getPopularity())
                        .setProducer(producerMapper.getProducerByMovieId(movieId))
                        .setRating(info.getRating())
                        .setPosterPath(info.getPosterPath())
                        .setRating(info.getRating())
                        .setVoteCount(info.getVoteCount());
                movies.add(vo);
            }
            return new PageInfo<>(movies);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
