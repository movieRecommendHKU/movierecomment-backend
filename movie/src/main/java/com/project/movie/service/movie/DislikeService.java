package com.project.movie.service.movie;

import com.project.movie.domain.DO.Dislike;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DislikeService {
    Dislike dislike(Dislike dislike);

    boolean removeDislike(Integer dislikeId);

    List<Dislike> getDislikesByUser(Integer userId);
}
