package com.project.movie.service.movie.kg;

import com.project.movie.config.Common;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.DTO.GraphNode;
import com.project.movie.domain.enums.UserMovieAction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public abstract class GraphService {
    // labels
    protected String LABEL_GENRES = "genres";
    protected String LABEL_ACTION = "action";
    protected String LABEL_MOVIE = "movie";
    protected String LABEL_PERSON = "person";
    protected String LABEL_USER = "user";

    // properties
    protected String PROP_USER_ID = "userId";
    protected String PROP_MOVIE_ID = "movieId";

    // relations: person -> movie
    protected String REL_ACT = "act";
    protected String REL_DIRECT = "direct";

    // relations: user -> movie
    protected String REL_COLLECT = UserMovieAction.COLLECT.name();
    protected static final String REL_HIGH_RATE = UserMovieAction.HIGH_RATE.name();
    protected static final String REL_LOW_RATE = UserMovieAction.LOW_RATE.name();
    protected static final String REL_DISLIKE = UserMovieAction.DISLIKE.name();

    public abstract boolean insertUser(User user);

    public abstract boolean takeAction(Integer userId, Integer movieId, String action);

    public abstract boolean deleteAction(Integer userId, Integer movieId, String action);

    public abstract Map<UserMovieAction, List<GraphNode>> getUserActionsToMovies(Integer userId);

    public abstract List<String> getUserPreference(Integer userId);

    public abstract boolean updateUserPreference(Integer userId, List<String> remove, List<String> insert);

    public static String getRateRelName(double rate) {
        if (rate >= Common.HIGH_RATING) return REL_HIGH_RATE;
        else if (rate <= Common.LOW_RATING) return REL_LOW_RATE;
        else return null;
    }
}
