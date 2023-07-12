package com.project.movie.service.recommend.impl;

import com.project.movie.domain.DO.Movie;
import com.project.movie.service.recommend.RecommendService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendServiceImpl implements RecommendService {

	@Override
	public List<Movie> getAllMovies(Integer userId) {
		return null;
	}

	@Override
	public List<Movie> getMoviesByPolicy(Integer userId, String policy) {
		return null;
	}
}
