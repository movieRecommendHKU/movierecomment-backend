package com.project.movie.service.search;

import com.project.movie.domain.DTO.UserSimilarityInfo;
import com.project.movie.domain.enums.UserSearchResult;
import org.springframework.stereotype.Controller;

@Controller
public interface UserSearchDataService {
    UserSearchResult initialElasticSearch() throws Exception;

    UserSearchResult addUserToElasticSearch(UserSimilarityInfo userSimilarityInfo) throws Exception;

    UserSearchResult deleteUserInElasticSearch(Integer userId) throws Exception;

}
