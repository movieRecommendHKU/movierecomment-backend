package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieMapper {

	List<Movie> getMovieList();

}