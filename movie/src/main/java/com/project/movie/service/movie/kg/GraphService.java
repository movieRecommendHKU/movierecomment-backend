package com.project.movie.service.movie.kg;

import com.project.movie.config.Common;
import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.DTO.GraphNode;
import com.project.movie.domain.enums.UserMovieAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public abstract List<String> getUserPreferenceLabel(Integer userId);

    public abstract List<Genre> getUserPreferenceGenre(Integer userId);

    public abstract boolean updateUserPreference(Integer userId, List<String> remove, List<String> insert);

    public abstract List<Integer> getMoviesByGenre(Genre genre, int limit);

    public abstract List<Integer> getMoviesInDepth(Integer startMovieId, int depth, int limit);

    public static String getRateRelName(double rate) {
        log.info("rate:{}, high rate threshold:{}, low rate threshold:{}", rate, Common.HIGH_RATING, Common.LOW_RATING);
        if (rate >= Common.HIGH_RATING) return REL_HIGH_RATE;
        else if (rate <= Common.LOW_RATING) return REL_LOW_RATE;
        else return null;
    }

    protected static String genreToLabel(Genre genre) {
        return genre.getGenreId() + "," + genre.getGenreName();
    }

    protected static Genre labelToGenre(String label) {
        String[] split = label.split(",");
        return new Genre().setGenreId(Integer.parseInt(split[0])).setGenreName(split[1]);
    }

    protected static List<String> convertLabelList(Object rawLabels, List<String> remove) {
        if (rawLabels instanceof List<?>) {
            return ((List<?>) rawLabels).stream()
                    .map(Object::toString)
                    .filter(label -> !remove.contains(label))
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }
}
