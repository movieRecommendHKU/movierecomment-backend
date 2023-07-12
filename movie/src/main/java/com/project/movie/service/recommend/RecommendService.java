package com.project.movie.service.recommend;

import com.project.movie.domain.DO.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecommendService {
	List<Movie> getAllMovies(Integer userId);

	List<Movie> getMoviesByPolicy(Integer userId, String policy);
}
