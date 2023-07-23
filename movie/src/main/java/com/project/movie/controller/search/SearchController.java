package com.project.movie.controller.search;

import com.alibaba.fastjson.JSONObject;
import com.project.movie.domain.DO.UserSimilarityInfo;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.search.InitMovieSearchDataService;
import com.project.movie.service.search.SearchMoviesService;
import com.project.movie.service.search.SearchSimilarUserService;
import com.project.movie.service.search.UserSearchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    InitMovieSearchDataService initMovieSearchDataService;

    @Autowired
    SearchMoviesService searchMoviesService;

    @Autowired
    UserSearchDataService userSearchDataService;

    @Autowired
    SearchSimilarUserService searchSimilarUserService;

    @GetMapping("/InitializeMovie")
    public BaseResponse initializeMovie() {
        try {
            Boolean isSuccess = initMovieSearchDataService.addMovieToElasticSearch();
            return isSuccess ? new BaseResponse().setStatus(true).setMsg("Initialize Movies Success!")
                    : new BaseResponse().setStatus(false).setMsg("Initialize Movies Fail!");
        }catch (Exception e){
            e.printStackTrace();
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
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Search Movies Fail!");
        }
    }

    @PostMapping("/searchBySentences")
    public BaseResponse searchBySentences(@RequestBody Map<String, Object> maps) {
        try {
            List<MovieVO> movies = searchMoviesService.searchBySentences(maps.get("string_sentences").toString(), Integer.valueOf(maps.get("k").toString()));
            return movies == null ? new BaseResponse().setStatus(false).setMsg("No related movies!")
                    : new BaseResponse().setStatus(true).setMsg("Search Movies Success!").setContent(movies);
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Search Movies Fail!");
        }
    }

    @GetMapping("/InitializeUserSimilarity")
    public BaseResponse initializeUserSimilarity() {
        try {
            Boolean isSuccess = userSearchDataService.initialElasticSearch();
            return isSuccess ? new BaseResponse().setStatus(true).setMsg("Initialize User Success!")
                    : new BaseResponse().setStatus(false).setMsg("Initialize User Fail!");
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Initialize User Fail!");
        }
    }

    @PostMapping("/addUser")
    public BaseResponse addUserSimilarity(@RequestBody Map<String, Object> maps) {
        try {
            List<Double> similarity = JSONObject.parseArray(maps.get("similarity").toString(), Double.class);
            Integer id = Integer.valueOf(maps.get("userId").toString());
            UserSimilarityInfo userSimilarityInfo = new UserSimilarityInfo(id, similarity);
            Boolean res = userSearchDataService.addUserToElasticSearch(userSimilarityInfo);
            return res ? new BaseResponse().setStatus(true).setMsg("Update User Success!")
                    : new BaseResponse().setStatus(true).setMsg("Add User Success!");
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Get User Fail!");
        }
    }

    @PostMapping("/searchByUserSimilarity")
    public BaseResponse searchByUserSimilarity(@RequestBody Map<String, Object> maps){
        try {
            List<Double> similarity = JSONObject.parseArray(maps.get("similarity").toString(), Double.class);
            Integer id = Integer.valueOf(maps.get("userId").toString());
            UserSimilarityInfo userSimilarityInfo = new UserSimilarityInfo(id, similarity);
            Integer k = Integer.valueOf(maps.get("k").toString());
            List<Integer> user_Ids = searchSimilarUserService.searchByUserSimilarity(userSimilarityInfo,k);
            return user_Ids == null ? new BaseResponse().setStatus(false).setMsg("No similar users!")
                    : new BaseResponse().setStatus(true).setMsg("Search Similar Users Success!").setContent(user_Ids);
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Search Similar Users Fail!");
        }
    }
}


