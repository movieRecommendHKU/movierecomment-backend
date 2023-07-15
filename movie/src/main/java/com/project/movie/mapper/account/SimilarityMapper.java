package com.project.movie.mapper.account;

import com.project.movie.domain.DO.User;
import com.project.movie.domain.DO.UserSimilarity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimilarityMapper {

	List<UserSimilarity> getSimilarUsers(Integer userId);
}
