package com.project.movie.service.movie.impl;

import com.project.movie.domain.DO.Collect;
import com.project.movie.mapper.movie.CollectMapper;
import com.project.movie.service.movie.CollectService;
import jakarta.annotation.Resource;

import java.util.List;

public class CollectServiceImpl implements CollectService {

    @Resource
    CollectMapper collectMapper;

    @Override
    public Collect collect(Collect collect) {
        collectMapper.collect(collect);
        return collect;
    }

    @Override
    public boolean removeCollect(Integer collectId) {
        return 1 == collectMapper.removeCollect(collectId);
    }

    @Override
    public List<Collect> getCollectionsByUser(Integer userId) {
        return collectMapper.getCollectionsByUser(userId);
    }
}
