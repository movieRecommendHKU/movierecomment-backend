package com.project.movie.controller.search;

import com.project.movie.service.search.InitMovieSearchDataService;
import com.project.movie.service.search.SearchMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class MovieSearchController {

    @Autowired
    InitMovieSearchDataService initMovieSearchDataService;

    @Autowired
    SearchMoviesService searchMoviesService;

    @GetMapping("/InitializeMovie")
    public Boolean initializeMovie() throws Exception {
        return initMovieSearchDataService.addMovieToElasticSearch();
    }

    @PostMapping("/searchByWords")
    public List<Integer> searchByWords(@RequestBody Map<String, Object> maps) throws Exception{
        return searchMoviesService.searchByKeywords(maps.get("string_keywords").toString(), Integer.valueOf(maps.get("k").toString()));
    }

    @PostMapping("/searchBySentences")
    public List<Integer> searchBySentences(@RequestBody Map<String, Object> maps) throws Exception{
        return searchMoviesService.searchBySentences(maps.get("string_sentences").toString(), Integer.valueOf(maps.get("k").toString()));
    }
}
