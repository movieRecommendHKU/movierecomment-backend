package com.project.movie.service.movie;

import com.project.movie.domain.DO.Collect;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectService {
    Collect collect(Collect collect);

    boolean removeCollect(Integer collectId);

    List<Collect> getCollectionsByUser(Integer userId);
}
