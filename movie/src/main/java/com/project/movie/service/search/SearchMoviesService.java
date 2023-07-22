package com.project.movie.service.search;

import com.project.movie.domain.VO.MovieVO;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface SearchMoviesService {

    List<MovieVO> searchByKeywords(String input_words, Integer k);

    List<MovieVO> searchBySentences(String input_sentences, Integer k);
}
