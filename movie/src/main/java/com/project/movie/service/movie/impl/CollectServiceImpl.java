package com.project.movie.service.movie.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
import com.project.movie.mapper.movie.CollectMapper;
import com.project.movie.service.movie.CollectService;
import com.project.movie.utils.Neo4jUtil;
import jakarta.annotation.Resource;

import java.util.List;

public class CollectServiceImpl implements CollectService {

    @Resource
    CollectMapper collectMapper;

    @Resource
    Neo4jUtil neo4jUtil;

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
    public PageInfo<Collect> getCollectionsByUser(Integer page, Integer pageSize, Integer userId) {
        PageHelper.startPage(page, pageSize);
        try {
            List<Collect> collectList = collectMapper.getCollectionsByUser(userId);
            return new PageInfo<>(collectList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
