package com.project.movie.service.movie;

import com.project.movie.domain.DO.Comment;
import com.project.movie.domain.DO.Dislike;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    Comment comment(Comment comment);

    boolean removeComment(Integer commentId);

    List<Comment> getCommentsByMovie(Integer movieId);

    List<Comment> getCommentsByUser(Integer userId);
}
