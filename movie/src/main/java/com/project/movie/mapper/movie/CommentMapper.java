package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Comment;
import com.project.movie.domain.DO.Dislike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int comment(Comment comment);

    int removeComment(Integer commentId);

    List<Comment> getCommentsByMovie(Integer movieId);

    List<Comment> getCommentsByUser(Integer userId);
}
