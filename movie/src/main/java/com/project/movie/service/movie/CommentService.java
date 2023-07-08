package com.project.movie.service.movie;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Integer comment(Comment comment);

    boolean removeComment(Integer commentId);

    PageInfo<Comment> getCommentsByMovie(Integer page,
                                         Integer pageSize,
                                         Integer movieId);

    PageInfo<Comment> getCommentsByUser(Integer page,
                                        Integer pageSize,
                                        Integer userId);
}
