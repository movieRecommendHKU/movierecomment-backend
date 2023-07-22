package com.project.movie.service.movie.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.MovieSimilarity;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.mapper.cast.CastMapper;
import com.project.movie.mapper.director.DirectorMapper;
import com.project.movie.mapper.movie.MovieMapper;
import com.project.movie.mapper.producer.ProducerMapper;
import com.project.movie.service.movie.base.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
            List<MovieVO> movies = moviesBaseInfo.stream().map(this::assembleMovieVO).toList();
            return new PageInfo<>(movies);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MovieVO assembleMovieVO(Movie movie) {
        MovieVO vo = new MovieVO()
                .setMovieId(movie.getMovieId())
                .setMovieName(movie.getMovieName())
                .setOverview(movie.getOverview())
                .setCasts(castMapper.getCastsByMovieId(movie.getMovieId()))
                .setDirector(directorMapper.getDirectorByMovie(movie.getDirector()))
                .setPopularity(movie.getPopularity())
                .setProducer(producerMapper.getProducerByMovie(movie.getProducer()))
                .setRating(movie.getRating())
                .setPosterPath(movie.getPosterPath())
                .setRating(movie.getRating())
                .setVoteCount(movie.getVoteCount());
        return vo;
    }

    @Override
    public List<Movie> batchAssembleMovie(List<Integer> movieIds) {
        return movieMapper.getMovieListByIds(movieIds);
    }

    @Override
    public List<MovieSimilarity> getSimilarMovies(Integer movieId) {
        return movieMapper.getSimilarMovies(movieId);
    }

    @Override
    public List<Movie> getHotMoviesThisYear() {
        return movieMapper.getThisYearHotMovies();
    }

}
