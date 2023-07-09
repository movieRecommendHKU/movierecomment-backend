package com.project.movie.service.movie.action.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Comment;
import com.project.movie.mapper.movie.CommentMapper;
import com.project.movie.service.movie.action.CommentService;
//import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
//import javax.annotation.Resource;
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public Integer comment(Comment comment) {
        return commentMapper.comment(comment);
    }

    @Override
    public boolean removeComment(Integer commentId) {
        return 1 == commentMapper.removeComment(commentId);
    }

    @Override
    public PageInfo<Comment> getCommentsByMovie(Integer page, Integer pageSize, Integer movieId) {
        PageHelper.startPage(page, pageSize);
        try {
            List<Comment> commentList = commentMapper.getCommentsByMovie(movieId);
            return new PageInfo<>(commentList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public PageInfo<Comment> getCommentsByUser(Integer page, Integer pageSize, Integer userId) {
        PageHelper.startPage(page, pageSize);
        try {
            List<Comment> commentList = commentMapper.getCommentsByUser(userId);
            return new PageInfo<>(commentList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
