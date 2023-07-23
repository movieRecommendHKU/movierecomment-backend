package com.project.movie.service.movie.action;

import com.project.movie.domain.DO.Dislike;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DislikeService {
    Integer dislike(Dislike dislike);

    List<Dislike> getUserDislikes(Integer userId);

    boolean isDislike(Dislike dislike);
}
