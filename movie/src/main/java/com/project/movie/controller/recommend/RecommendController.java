package com.project.movie.controller.recommend;

import com.project.movie.domain.DO.User;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.recommend.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/recommend")
public class RecommendController {
    @Autowired
    RecommendService recommendService;

    @GetMapping("/get_all")
    public BaseResponse getRecommends(@RequestBody User user) {
        return null;
    }

}
