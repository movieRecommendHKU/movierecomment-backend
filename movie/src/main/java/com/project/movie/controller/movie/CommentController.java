package com.project.movie.controller.movie;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Comment;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public BaseResponse comment(@RequestBody Comment comment) {
        Integer id = commentService.comment(comment);
        return id != null ?
                new BaseResponse().setStatus(true).setContent("Comment successfully.").setContent(id) :
                new BaseResponse().setStatus(false).setContent("Comment failed.");
    }

    @PostMapping("/remove_comment")
    public BaseResponse removeComment(@RequestBody Comment comment) {
        Integer id = commentService.comment(comment);
        return id != null ?
                new BaseResponse().setStatus(true).setContent("Remove comment successfully.").setContent(id) :
                new BaseResponse().setStatus(false).setContent("Remove comment failed.");
    }

    @GetMapping("/get_movie_comment")
    public BaseResponse getMovieComment(@RequestParam Integer page,
                                        @RequestParam Integer pageSize,
                                        @RequestParam Integer movieId) {
        PageInfo<Comment> res = commentService.getCommentsByMovie(page, pageSize, movieId);
        return res != null ?
                new BaseResponse().setStatus(true).setContent("Get comments of movie." + movieId).setContent(res) :
                new BaseResponse().setStatus(false).setContent("Get comments of movie failed.");
    }

    @GetMapping("/get_user_comment")
    public BaseResponse getUserComment(@RequestParam Integer page,
                                       @RequestParam Integer pageSize,
                                       @RequestParam Integer userId) {
        PageInfo<Comment> res = commentService.getCommentsByUser(page, pageSize, userId);
        return res != null ?
                new BaseResponse().setStatus(true).setContent("Get comments from user." + userId).setContent(res) :
                new BaseResponse().setStatus(false).setContent("Get comments from user failed.");
    }

}
