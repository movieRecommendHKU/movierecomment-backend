package com.project.movie.controller.movie.action;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.domain.enums.UserMovieAction;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.action.DislikeService;
import com.project.movie.service.movie.kg.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                UserMovieAction.DISLIKE.name());
        return dbRes && kgRes ?
                new BaseResponse().setStatus(true).setContent("Dislike successfully.") :
                new BaseResponse().setStatus(false).setContent("Dislike failed.");
    }
}
