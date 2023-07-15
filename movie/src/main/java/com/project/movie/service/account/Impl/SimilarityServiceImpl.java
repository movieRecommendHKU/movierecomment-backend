package com.project.movie.service.account.Impl;

import com.project.movie.domain.DO.User;
import com.project.movie.domain.DO.UserSimilarity;
import com.project.movie.mapper.account.SimilarityMapper;
import com.project.movie.service.account.SimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimilarityServiceImpl implements SimilarityService {
	@Autowired
	SimilarityMapper similarityMapper;

	@Override
	public List<UserSimilarity> getSimilarByUser(Integer userId) {
		return similarityMapper.getSimilarUsers(userId);
	}
}
