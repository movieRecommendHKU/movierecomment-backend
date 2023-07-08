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
    public Integer dislike(Dislike dislike) {
        return dislikeMapper.dislike(dislike);
    }

}
