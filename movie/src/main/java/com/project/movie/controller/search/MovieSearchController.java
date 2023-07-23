package com.project.movie.controller.search;

import com.project.movie.domain.VO.MovieVO;
import com.project.movie.domain.response.BaseResponse;
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
    public BaseResponse initializeMovie() {
        try {
            Boolean isSuccess = initMovieSearchDataService.addMovieToElasticSearch();
            return isSuccess ? new BaseResponse().setStatus(true).setMsg("Initialize Movies Success!")
                    : new BaseResponse().setStatus(true).setMsg("Initialize Movies Fail!");
        }catch (Exception e){
            return new BaseResponse().setStatus(false).setMsg("Initialize Movies Fail!");
        }
    }

    @PostMapping("/searchByWords")
    public BaseResponse searchByWords(@RequestBody Map<String, Object> maps) {
        try {
            List<MovieVO> movies = searchMoviesService.searchByKeywords(maps.get("string_keywords").toString(), Integer.valueOf(maps.get("k").toString()));
            return movies == null ? new BaseResponse().setStatus(false).setMsg("No related movies!")
                    : new BaseResponse().setStatus(true).setMsg("Search Movies Success!").setContent(movies);
        }catch (Exception e){
            return new BaseResponse().setStatus(false).setMsg("Search Movies Fail!");
        }
    }

    @PostMapping("/searchBySentences")
    public BaseResponse searchBySentences(@RequestBody Map<String, Object> maps) throws Exception{
        try {
            List<MovieVO> movies = searchMoviesService.searchBySentences(maps.get("string_sentences").toString(), Integer.valueOf(maps.get("k").toString()));
            return movies == null ? new BaseResponse().setStatus(false).setMsg("No related movies!")
                    : new BaseResponse().setStatus(true).setMsg("Search Movies Success!").setContent(movies);
        }catch (Exception e){
            return new BaseResponse().setStatus(false).setMsg("Search Movies Fail!");
        }
    }
}
