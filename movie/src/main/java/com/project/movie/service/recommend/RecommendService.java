package com.project.movie.service.recommend;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecommendService {
	List<Integer> getAllMovies(Integer userId);

	List<Integer> getMoviesByPolicy(Integer userId, String policy);

}
