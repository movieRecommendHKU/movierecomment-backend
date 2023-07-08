package com.project.movie.service.movie.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Cast;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.mapper.cast.CastMapper;
import com.project.movie.mapper.director.DirectorMapper;
import com.project.movie.mapper.movie.MovieMapper;
import com.project.movie.mapper.producer.ProducerMapper;
import com.project.movie.service.movie.MovieService;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

public class MovieServiceImpl implements MovieService {
	@Resource
	MovieMapper movieMapper;

	@Resource
	CastMapper castMapper;

	@Resource
	DirectorMapper directorMapper;

	@Resource
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
			for(Movie info: moviesBaseInfo) {
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
