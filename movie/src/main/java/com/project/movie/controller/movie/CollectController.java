package com.project.movie.controller.movie;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.CollectService;
import com.project.movie.service.movie.GraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
@Slf4j
public class CollectController {
    @Autowired
    CollectService collectService;

    @Autowired
    GraphService graphService;

    @PostMapping("/collect")
    public BaseResponse collect(@RequestBody Collect collect) {
        Collect res = collectService.collect(collect);

        boolean dbCollect = res != null;
        boolean kgCollect = graphService.takeAction(
                new User().setUserId(collect.getUserId()),
                new Movie().setMovieId(collect.getMovieId()),
                GraphService.REL_COLLECT);

        if (dbCollect && kgCollect) {
            return BaseResponse.success("Collect successfully.", res);
        } else if (dbCollect) {
            boolean rm = collectService.removeCollect(collect.getCollectId());
            log.error("Remove DB collect id = {}: {}.", collect.getCollectId(), rm);
            return BaseResponse.error("KG collect failed.");
        } else if (kgCollect) {
            boolean rm = graphService.deleteAction(
                    new User().setUserId(collect.getUserId()),
                    new Movie().setMovieId(collect.getMovieId()),
                    GraphService.REL_COLLECT);
            log.error("Remove KG collect: {}.", rm);
            return BaseResponse.error("DB collect failed.");
        } else {
            return BaseResponse.error("Collect failed.");
        }
    }

    @PostMapping("/rm_collect")
    public BaseResponse removeCollect(@RequestParam Integer collectId) {
        boolean res = collectService.removeCollect(collectId);
        return res ?
                BaseResponse.success("Remove collect successfully.", true) :
                BaseResponse.error("Remove collect failed.");
    }

    @GetMapping("/get_user_collection")
    public BaseResponse getCollectionsByUser(@RequestParam Integer page,
                                             @RequestParam Integer pageSize,
                                             @RequestParam Integer userId) {
        PageInfo<Collect> res = collectService.getCollectionsByUser(page, pageSize, userId);
        return res != null ?
                BaseResponse.success("Get collections.", res) :
                BaseResponse.error("Get collections. failed.");
    }
}
