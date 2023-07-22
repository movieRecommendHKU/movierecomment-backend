package com.project.movie.service.search;

import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface SearchMoviesService {

    List<Integer> searchByKeywords(String input_words, Integer k);

    List<Integer> searchBySentences(String input_sentences, Integer k);
}
