package com.project.movie.service.movie.action.impl;

import com.project.movie.domain.DO.Dislike;
import com.project.movie.mapper.movie.DislikeMapper;
import com.project.movie.service.movie.action.DislikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

//import javax.annotation.Resource;
@Service
public class DislikeServiceImpl implements DislikeService {

    @Autowired
    DislikeMapper dislikeMapper;

    @Override
    public Integer dislike(Dislike dislike) {
        return dislikeMapper.dislike(dislike);
    }

    @Override
    public List<Dislike> getUserDislikes(Integer userId){
        return dislikeMapper.getDislikesByUser(userId);
    }

    @Override
    public boolean isDislike(Dislike dislike) {
        return dislikeMapper.getDislike(dislike) != null;
    }
}
