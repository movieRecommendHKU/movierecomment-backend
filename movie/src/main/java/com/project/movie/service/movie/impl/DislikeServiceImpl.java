package com.project.movie.service.movie.impl;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.mapper.movie.DislikeMapper;
import com.project.movie.service.movie.DislikeService;
import jakarta.annotation.Resource;

import java.util.List;

public class DislikeServiceImpl implements DislikeService {

    @Resource
    DislikeMapper dislikeMapper;

    @Override
    public Dislike dislike(Dislike dislike) {
        dislikeMapper.dislike(dislike);
        return dislike;
    }

    @Override
    public boolean removeDislike(Integer dislikeId) {
        return 1 == dislikeMapper.removeDislike(dislikeId);
    }

    @Override
    public List<Dislike> getDislikesByUser(Integer userId) {
        return dislikeMapper.getDislikesByUser(userId);
    }
}
