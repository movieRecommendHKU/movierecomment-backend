package com.project.movie.service.movie.action.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
import com.project.movie.mapper.movie.CollectMapper;
import com.project.movie.service.movie.action.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
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
    public PageInfo<Collect> getCollectionsByUser(Integer page, Integer pageSize, Integer userId) {
        PageHelper.startPage(page, pageSize);
        try {
            List<Collect> collectList = collectMapper.getCollectionsByUser(userId);
            return new PageInfo<>(collectList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
