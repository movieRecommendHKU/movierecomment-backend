package com.project.movie.service.account;

import com.project.movie.domain.DTO.UserSimilarity;

import java.util.List;

public interface SimilarityService {

	List<UserSimilarity> getSimilarByUser(Integer userId);

}
