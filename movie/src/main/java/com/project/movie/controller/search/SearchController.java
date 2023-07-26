package com.project.movie.controller.search;

import com.alibaba.fastjson.JSONObject;
import com.project.movie.domain.DTO.UserSimilarityInfo;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.domain.enums.UserSearchResult;
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
        // 初始化创建index
        try {
            UserSearchResult result = userSearchDataService.initialElasticSearch();
            if (result.getType().equals(1L)){
                return new BaseResponse().setStatus(false).setMsg(result.getDesc());
            }else{
                return new BaseResponse().setStatus(true).setMsg(result.getDesc());
            }
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Initialize User Error!");
        }
    }

    @PostMapping("/addUser")
    public BaseResponse addUserSimilarity(@RequestBody Map<String, Object> maps) {
        // 本地用来进行加入/更新数据测试接口
        try {
            List<Double> vector = JSONObject.parseArray(maps.get("vector").toString(), Double.class);
            Integer id = Integer.valueOf(maps.get("userId").toString());
            UserSimilarityInfo userSimilarityInfo = new UserSimilarityInfo(id, vector);
            UserSearchResult result = userSearchDataService.addUserToElasticSearch(userSimilarityInfo);
            if (result.getType().equals(4L)||result.getType().equals(6L)){
                return new BaseResponse().setStatus(true).setMsg(result.getDesc());
            }else{
                return new BaseResponse().setStatus(false).setMsg(result.getDesc());
            }
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Add/Update User Error!");
        }
    }

    @PostMapping("/deleteUser")
    public BaseResponse deleteUserSimilarity(@RequestBody Map<String, Object> maps) {
        // 本地用来进行加入/更新数据测试接口
        try {
            Integer id = Integer.valueOf(maps.get("userId").toString());
            UserSearchResult result = userSearchDataService.deleteUserInElasticSearch(id);
            if (result.getType().equals(9L)){
                return new BaseResponse().setStatus(true).setMsg(result.getDesc());
            }else {
                return new BaseResponse().setStatus(false).setMsg(result.getDesc());
            }

        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Delete User Error!");
        }
    }

    @PostMapping("/getUserVector")
    public BaseResponse getUserVector(@RequestBody Map<String, Object> maps){
        try {
            Integer id = Integer.valueOf(maps.get("userId").toString());
            List<Double> vector = searchSimilarUserService.getVectorByUserId(id);
            return vector.size() == 0 ? new BaseResponse().setStatus(false).setMsg("The vector is empty!")
                    : new BaseResponse().setStatus(true).setMsg("Get User Vector Success!").setContent(vector);
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Get User Vector Fail!");
        }
    }

    @PostMapping("/searchByUserSimilarity")
    public BaseResponse searchByUserSimilarity(@RequestBody Map<String, Object> maps){
        // 本地用来进行搜索数据测试接口
        try {
            Integer id = Integer.valueOf(maps.get("userId").toString());
            Integer k = Integer.valueOf(maps.get("k").toString());
            List<Integer> user_Ids = searchSimilarUserService.searchByUserSimilarity(id,k);
            return user_Ids == null ? new BaseResponse().setStatus(false).setMsg("No similar users!")
                    : new BaseResponse().setStatus(true).setMsg("Search Similar Users Success!").setContent(user_Ids);
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse().setStatus(false).setMsg("Search Similar Users Fail!");
        }
    }
}


