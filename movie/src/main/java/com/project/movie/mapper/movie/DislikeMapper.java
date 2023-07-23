package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Collect;
import com.project.movie.domain.DO.Dislike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DislikeMapper {
    int dislike(Dislike dislike);

    List<Dislike> getDislikesByUser(Integer userId);

    Dislike getDislike(Dislike dislike);
}
