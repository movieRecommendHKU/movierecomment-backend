package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Collect;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectMapper {
    int collect(Collect collect);

    int removeCollect(Integer collectId);

    List<Collect> getCollectionsByUser(Integer userId);

    List<Collect> getRecentByUser(Integer userId);

    List<Collect> getRecentBySimilarUsers(List<Integer> users);
}
