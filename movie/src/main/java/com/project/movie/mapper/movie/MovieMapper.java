package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.MovieSimilarity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieMapper {

	List<Movie> getMovieList();

	List<Movie> getMovieListByIds(List<Integer> ids);

	List<MovieSimilarity> getSimilarMovies(Integer movieId);

	List<Movie> getThisYearHotMovies();
}
