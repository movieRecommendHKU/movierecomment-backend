package com.project.movie.controller.recommend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.domain.enums.RecommenderEnum;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.base.MovieService;
import com.project.movie.service.recommend.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    RecommendService recommendService;

    @Autowired
    MovieService movieService;

    @GetMapping("/get_all")
    public BaseResponse getRecommends(@RequestBody User user) {
        return null;
    }

    /**
     * used in homepage popular part
     * @return List<Movie>
     */
    @GetMapping("/get_by_policy")
    public BaseResponse getRecommendsByPolicy(@RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "5") int pageSize,
                                             @RequestParam(required = false) String orderBy,
                                             @RequestParam Integer userId,
                                             @RequestParam String policy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        try {
            List<Integer> ids = recommendService.getMoviesByPolicy(userId, RecommenderEnum.valueOf(policy));
            List<MovieVO> movies = movieService.batchAssembleMovie(ids);
            return movies == null ? new BaseResponse().setStatus(false).setMsg("No recommend movies!")
                    : new BaseResponse().setStatus(true).setMsg("Get movies success").setContent(new PageInfo<>(movies));
        } catch (Exception e) {
            return new BaseResponse().setStatus(false).setMsg("Get Movies Failure! ");
        }
    }
}
