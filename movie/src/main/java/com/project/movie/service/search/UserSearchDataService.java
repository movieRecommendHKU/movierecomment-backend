package com.project.movie.service.search;

import com.project.movie.domain.DTO.UserSimilarityInfo;
import org.springframework.stereotype.Controller;

@Controller
public interface UserSearchDataService {
    Boolean initialElasticSearch() throws Exception;

    Boolean addUserToElasticSearch(UserSimilarityInfo userSimilarityInfo) throws Exception;

}
