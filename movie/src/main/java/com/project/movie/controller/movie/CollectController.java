package com.project.movie.controller.movie;

import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class CollectController {
    @Autowired
    CollectService collectService;

    @PostMapping("/collect")
    public BaseResponse collect(@RequestBody Collect collect) {
        Collect res = collectService.collect(collect);
        return res.getCollectId() != null ?
                BaseResponse.success("Collect successfully.", res) :
                BaseResponse.error("Collect failed.");
    }

    @PostMapping("/rm_collect")
    public BaseResponse removeCollect(@RequestParam Integer collectId) {
        boolean res = collectService.removeCollect(collectId);
        return res ?
                BaseResponse.success("Remove collect successfully.", true) :
                BaseResponse.error("Remove collect failed.");
    }

    @PostMapping("/get_user_collection")
    public BaseResponse getCollectionsByUser(Integer userId) {
        return null;
    }
}
