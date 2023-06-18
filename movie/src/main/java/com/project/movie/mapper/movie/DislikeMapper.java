package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Dislike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DislikeMapper {
    int dislike(Dislike dislike);

    int removeDislike(Integer dislikeId);

    List<Dislike> getDislikesByUser(Integer userId);
}
