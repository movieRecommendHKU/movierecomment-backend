package com.project.movie.service.movie.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Comment;
import com.project.movie.mapper.movie.CommentMapper;
import com.project.movie.service.movie.CommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
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
