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
import com.project.movie.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            List<Movie> movies = movieMapper.getMovieList();
            PageInfo<Movie> moviePageInfo = new PageInfo<>(movies);
            PageInfo<MovieVO> movieVOPageInfo = PageUtil.PageInfo2PageInfoVo(moviePageInfo);
            movies.forEach(movie->movieVOPageInfo.getList().add(assembleMovieVO(movie)));
            return movieVOPageInfo;
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
                .setProducer(movie.getProducer() != null ? producerMapper.getProducerByMovie(movie.getProducer()) : null)
                .setRating(movie.getRating())
                .setPosterPath(movie.getPosterPath())
                .setRating(movie.getRating())
                .setVoteCount(movie.getVoteCount())
                .setReleaseDate(movie.getReleaseDate());
        return vo;
    }

    @Override
    public List<Movie> batchAssembleMovie(List<Integer> movieIds) {
        List<Movie> movies = movieMapper.getMovieListByIds(movieIds);
        return movies;
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
