package com.project.movie.service.search;

import com.project.movie.domain.DTO.UserSimilarityInfo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface SearchSimilarUserService {
    List<Integer> searchByUserSimilarity(UserSimilarityInfo userSimilarityInfo, Integer k);
}
