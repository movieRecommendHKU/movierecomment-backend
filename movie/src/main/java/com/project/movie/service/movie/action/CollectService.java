package com.project.movie.service.movie.action;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.DO.Collect;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectService {
    Collect collect(Collect collect);

    boolean removeCollect(Integer collectId);

    PageInfo<Collect> getCollectionsByUser(Integer page, Integer pageSize, String order, Integer userId);

    /*
    return 10 recent collected movies
     */
    List<Collect> getRecentCollectionsByUser(Integer userId);

}
