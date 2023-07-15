package com.project.movie.service.movie.base;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.MovieSimilarity;
import com.project.movie.domain.VO.MovieVO;

import java.util.List;

public interface MovieService {

	PageInfo<MovieVO> getMovieCompleteList(int pageNum, int pageSize, String orderBy);

	List<MovieSimilarity> getSimilarMovies(Integer movieId);

	/**
	 * return 50 most popular movies released in this year
	 */
	List<Movie> getHotMoviesThisYear();
}
