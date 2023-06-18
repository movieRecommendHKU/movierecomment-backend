package com.project.movie.service.movie.impl;

import com.project.movie.domain.DO.Comment;
import com.project.movie.mapper.movie.CommentMapper;
import com.project.movie.service.movie.CommentService;
import jakarta.annotation.Resource;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Override
    public Comment comment(Comment comment) {
        commentMapper.comment(comment);
        return comment;
    }

    @Override
    public boolean removeComment(Integer commentId) {
        return 1 == commentMapper.removeComment(commentId);
    }

    @Override
    public List<Comment> getCommentsByMovie(Integer movieId) {
        return commentMapper.getCommentsByMovie(movieId);
    }

    @Override
    public List<Comment> getCommentsByUser(Integer userId) {
        return commentMapper.getCommentsByUser(userId);
    }
}
