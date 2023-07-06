package com.project.movie.service.movie;

import com.project.movie.domain.DO.Actor;
import com.project.movie.domain.DO.Director;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GraphService {
    // tags
    String TAG_MOVIE = "movie";
    String TAG_PERSON = "person";
    String TAG_USER = "user";

    // properties
    String PROP_USER_ID = "userId";
    String PROP_MOVIE_ID = "movieId";

    // relations: person -> movie
    String REL_ACT = "act";
    String REL_DIRECT = "direct";

    // relations: user -> movie
    String REL_COLLECT = "collect";
    String REL_HIGH_RATE = "high_rate";
    String REL_LOW_RATE = "low_rate";
    String REL_DISLIKE = "dislike";

    boolean insertUser(User user);

    boolean takeAction(User user, Movie movie, String action);

    boolean deleteAction(User user, Movie movie, String action);
}
