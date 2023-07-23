package com.project.movie.controller.movie.action;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.enums.UserMovieAction;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.action.DislikeService;
import com.project.movie.service.movie.kg.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie/action/dislike")
public class DislikeController {
    @Autowired
    DislikeService dislikeService;

    @Autowired
    GraphService graphService;

    @PostMapping("/dislike")
    public BaseResponse dislike(@RequestBody Dislike dislike) {
        boolean dbRes = null != dislikeService.dislike(dislike);
        boolean kgRes = graphService.takeAction(
                dislike.getUserId(),
                dislike.getMovieId(),
                UserMovieAction.dislike.name());
        return dbRes && kgRes ?
                new BaseResponse().setStatus(true).setContent("Dislike successfully.") :
                new BaseResponse().setStatus(false).setContent("Dislike failed.");
    }

    @PostMapping("/is_dislike")
    public BaseResponse isDislike(@RequestBody Dislike dislike) {
        return dislikeService.isDislike(dislike) ?
                new BaseResponse().setStatus(true) :
                new BaseResponse().setStatus(false);
    }
}
