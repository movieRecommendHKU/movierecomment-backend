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
import com.project.movie.utils.PageUtil;
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


    /**
     * used in homepage popular part
     *
     * @return List<Movie>
     */
    @GetMapping("/get_by_policy")
    public BaseResponse getRecommendsByPolicy(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "5") int pageSize,
                                              @RequestParam(required = false) String orderBy,
                                              @RequestParam Integer userId,
                                              @RequestParam String policy) {
        List<Integer> ids = recommendService.getMoviesByPolicy(userId, RecommenderEnum.valueOf(policy));
        return idsToMovieVOPageInfo(pageNum, pageSize, orderBy, ids);
    }

    @GetMapping("/get_user_recommends")
    public BaseResponse getRecommendsByUser(@RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "5") int pageSize,
                                            @RequestParam(required = false) String orderBy,
                                            @RequestParam Integer userId) {
        List<Integer> ids = recommendService.getUserRecommendLog(userId);
        return idsToMovieVOPageInfo(pageNum, pageSize, orderBy, ids);
    }

    private BaseResponse idsToMovieVOPageInfo(int pageNum, int pageSize, String orderBy, List<Integer> ids) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        try {
            List<Movie> movieList = movieService.batchAssembleMovie(ids);
            PageInfo<Movie> moviePageInfo = new PageInfo<>(movieList);
            PageInfo<MovieVO> movieVOPageInfo = PageUtil.PageInfo2PageInfoVo(moviePageInfo);
            movieList.forEach(movie -> movieVOPageInfo.getList().add(movieService.assembleMovieVO(movie)));

            return new BaseResponse().setStatus(true).setMsg("Get movies success").setContent(movieVOPageInfo);
        } catch (Exception e) {
            return new BaseResponse().setStatus(false).setMsg("Get Movies Failure! ");
        }
    }
}
