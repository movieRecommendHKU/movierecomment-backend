package com.project.movie.mapper.account;

import com.project.movie.domain.DTO.UserSimilarity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimilarityMapper {

	List<UserSimilarity> getSimilarUsers(Integer userId);
}
