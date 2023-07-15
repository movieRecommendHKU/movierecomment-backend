package com.project.movie.service.movie.kg;

import com.project.movie.config.Common;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.DTO.GraphNode;
import com.project.movie.domain.enums.UserMovieAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public abstract class GraphService {
    // labels
    protected static final String LABEL_GENRES = "genres";
    protected static final String LABEL_ACTION = "action";
    protected static final String LABEL_MOVIE = "movie";
    protected static final String LABEL_PERSON = "person";
    protected static final String LABEL_USER = "user";

    // properties
    protected static final String PROP_USER_ID = "userId";
    protected static final String PROP_MOVIE_ID = "movieId";

    // relations: person -> movie
    protected static final String REL_ACT = "act";
    protected static final String REL_DIRECT = "direct";

    // relations: user -> movie
    protected static final String REL_COLLECT = UserMovieAction.collect.name();
    protected static final String REL_HIGH_RATE = UserMovieAction.high_rate.name();
    protected static final String REL_LOW_RATE = UserMovieAction.low_rate.name();
    protected static final String REL_DISLIKE = UserMovieAction.dislike.name();

    public abstract boolean insertUser(User user);

    public abstract boolean takeAction(Integer userId, Integer movieId, String action);

    public abstract boolean deleteAction(Integer userId, Integer movieId, String action);

    public abstract Map<UserMovieAction, List<GraphNode>> getUserActionsToMovies(Integer userId);

    public abstract List<String> getUserPreference(Integer userId);

    public abstract boolean updateUserPreference(Integer userId, List<String> remove, List<String> insert);

    public static String getRateRelName(double rate) {
        log.info("rate:{}, high rate threshold:{}, low rate threshold:{}", rate, Common.HIGH_RATING, Common.LOW_RATING);
        if (rate >= Common.HIGH_RATING) return REL_HIGH_RATE;
        else if (rate <= Common.LOW_RATING) return REL_LOW_RATE;
        else return null;
    }
}
