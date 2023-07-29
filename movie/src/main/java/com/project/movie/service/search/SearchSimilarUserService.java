package com.project.movie.service.search;

import com.project.movie.domain.DTO.UserSimilarity;
import com.project.movie.domain.DTO.UserSimilarityInfo;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public interface SearchSimilarUserService {

    List<Double> getVectorByUserId(Integer userId) throws IOException;
    List<UserSimilarity> searchByUserSimilarity(Integer userId, Integer k) throws IOException;



}
