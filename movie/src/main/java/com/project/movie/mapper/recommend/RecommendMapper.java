package com.project.movie.mapper.recommend;

import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.MovieSimilarity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendMapper {

	String getRecommendLog(Integer userId);

	int insertRecommendLog(Integer userId, String log);
}
