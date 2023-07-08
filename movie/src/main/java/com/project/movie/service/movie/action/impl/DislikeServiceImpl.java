package com.project.movie.service.movie.action.impl;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.mapper.movie.DislikeMapper;
import com.project.movie.service.movie.action.DislikeService;
//import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
@Service
public class DislikeServiceImpl implements DislikeService {

    @Resource
    DislikeMapper dislikeMapper;

    @Override
    public Integer dislike(Dislike dislike) {
        return dislikeMapper.dislike(dislike);
    }

}
