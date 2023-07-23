package com.project.movie.controller.movie.action;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.enums.UserMovieAction;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.action.CollectService;
import com.project.movie.service.movie.kg.GraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie/action/collect")
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
                collect.getUserId(),
                collect.getMovieId(),
                UserMovieAction.collect.name());

        if (dbCollect && kgCollect) {
            return new BaseResponse()
                    .setStatus(true)
                    .setMsg("Collect successfully.")
                    .setContent(res);
        } else if (dbCollect) {
            boolean rm = collectService.removeCollect(collect);
            log.error("Remove DB collect id = {}: {}.", collect.getCollectId(), rm);
            return new BaseResponse()
                    .setStatus(false)
                    .setMsg("KG collect failed.");
        } else if (kgCollect) {
            boolean rm = graphService.deleteAction(
                    collect.getUserId(),
                    collect.getMovieId(),
                    UserMovieAction.collect.name());
            log.error("Remove KG collect: {}.", rm);
            return new BaseResponse()
                    .setStatus(false)
                    .setMsg("DB collect failed.");
        } else {
            return new BaseResponse()
                    .setStatus(false)
                    .setMsg("Collect failed.");
        }
    }

    @PostMapping("/rm_collect")
    public BaseResponse removeCollect(@RequestBody Collect collect) {
        boolean dbRemove = collectService.removeCollect(collect);
        boolean kgRemove = graphService.deleteAction(
                collect.getUserId(),
                collect.getMovieId(),
                UserMovieAction.collect.name());
        if (dbRemove && kgRemove) {
            return new BaseResponse()
                    .setStatus(true)
                    .setMsg("Remove collect successfully.");
        } else if (dbRemove) {
            Collect rc = collectService.collect(collect);
            log.error("Recover remove DB collect, new id = {}: {}.", collect.getCollectId(), rc);
            return new BaseResponse()
                    .setStatus(false)
                    .setMsg("KG collect failed, collect id renewed.")
                    .setContent(rc);
        } else if (kgRemove) {
            boolean rc = graphService.takeAction(
                    collect.getUserId(),
                    collect.getMovieId(),
                    UserMovieAction.collect.name());
            log.error("Recover remove KG collect: {}.", rc);
            return new BaseResponse()
                    .setStatus(false)
                    .setMsg("DB collect failed.");
        } else {
            return new BaseResponse()
                    .setStatus(false)
                    .setMsg("Collect failed.");
        }
    }

    @GetMapping("/get_user_collection")
    public BaseResponse getCollectionsByUser(@RequestParam Integer page,
                                             @RequestParam Integer pageSize,
                                             @RequestParam String order,
                                             @RequestParam Integer userId) {
        PageInfo<Collect> res = collectService.getCollectionsByUser(page, pageSize, order, userId);
        return res != null ?
                new BaseResponse().setStatus(true).setMsg("Get collections.").setContent(res) :
                new BaseResponse().setStatus(true).setMsg("Get collections. failed.");
    }

    @PostMapping("/is_collected")
    public BaseResponse isCollected(@RequestBody Collect collect) {
        return collectService.isCollected(collect) ?
                new BaseResponse().setStatus(true) :
                new BaseResponse().setStatus(false);
    }
}
