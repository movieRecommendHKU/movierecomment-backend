package com.project.movie.service.search;

import com.project.movie.domain.DO.UserSimilarityInfo;
import com.project.movie.domain.VO.MovieVO;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface SearchSimilarUserService {
    List<Integer> searchByUserSimilarity(UserSimilarityInfo userSimilarityInfo, Integer k);
}
