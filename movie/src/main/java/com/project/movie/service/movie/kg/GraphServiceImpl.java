package com.project.movie.service.movie.kg;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.DTO.GraphNode;
import com.project.movie.domain.enums.UserMovieAction;
import com.project.movie.utils.Neo4jUtil;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class GraphServiceImpl extends GraphService {
    @Autowired
    Neo4jUtil neo4jUtil;

    @Override
    public boolean insertUser(User user) {
        try {
            String cql = "CREATE (e: %s {%s:%d})";
            cql = String.format(cql, LABEL_USER,
                    PROP_USER_ID,
                    user.getUserId());
            log.info("insertUser: {}", cql);
            neo4jUtil.executeCypherSql(cql);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }


    @Override
    public boolean takeAction(Integer userId, Integer movieId, String action) {
        try {
            String cql = "MATCH (e1: %s), (e2: %s) "
                    + "WHERE e1.%s=%d "
                    + "AND e2.%s=%d "
                    + "CREATE (e1)-[r:%s]->(e2)";
            cql = String.format(cql,
                    LABEL_USER, LABEL_MOVIE,
                    PROP_USER_ID, userId,
                    PROP_MOVIE_ID, movieId,
                    action);
            log.info("takeAction: {}", cql);
            neo4jUtil.executeCypherSql(cql);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteAction(Integer userId, Integer movieId, String action) {
        try {
            String cql = "MATCH (e1: %s)-[r:%s]->(e2: %s) "
                    + "WHERE e1.%s=%d "
                    + "AND e2.%s=%d "
                    + "DELETE r";
            cql = String.format(cql,
                    LABEL_USER, action, LABEL_MOVIE,
                    PROP_USER_ID, userId,
                    PROP_MOVIE_ID, movieId);
            log.info("deleteAction: {}", cql);
            neo4jUtil.executeCypherSql(cql);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Map<UserMovieAction, List<GraphNode>> getUserActionsToMovies(Integer userId) {
        List<Record> res;
        try {
            String cql = "MATCH (e1: %s)-[r]-(e2: %s) "
                    + "WHERE e1.%s=%d "
                    + "RETURN e2.%s as %s, "
                    + "labels(e2) as %s, "
                    + "type(r) as %s";
            cql = String.format(cql,
                    LABEL_USER, LABEL_MOVIE,
                    PROP_USER_ID, userId,
                    PROP_MOVIE_ID, PROP_MOVIE_ID,
                    LABEL_GENRES,
                    LABEL_ACTION);
            log.info("getUserActionsToMovies: {}", cql);
            res = neo4jUtil.executeCypherSql(cql);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        Map<UserMovieAction, List<GraphNode>> actionMovieMap = new HashMap<>() {{
            this.put(UserMovieAction.collect, new ArrayList<>());
            this.put(UserMovieAction.high_rate, new ArrayList<>());
            this.put(UserMovieAction.low_rate, new ArrayList<>());
            this.put(UserMovieAction.dislike, new ArrayList<>());
        }};

        for (Record record : res) {
            Map<String, Object> recordMap = record.asMap();
            UserMovieAction action = UserMovieAction.valueOf(recordMap.get(LABEL_ACTION).toString());
            Integer movieId = Integer.parseInt(recordMap.get(PROP_MOVIE_ID).toString());
            List<String> genres = convertLabelList(recordMap.get(LABEL_GENRES), Collections.singletonList(LABEL_MOVIE));

            actionMovieMap.get(action)
                    .add(new GraphNode()
                            .setNodeId(movieId)
                            .setLabels(genres)
                            .setType(LABEL_MOVIE));
        }
        return actionMovieMap;
    }

    @Override
    public List<String> getUserPreferenceLabel(Integer userId) {
        try {
            String cql = "MATCH (e1: %s) "
                    + "WHERE e1.%s=%d "
                    + "RETURN labels(e1) as %s";
            cql = String.format(cql,
                    LABEL_USER,
                    PROP_USER_ID, userId,
                    LABEL_GENRES);
            log.info("getUserPreference: {}", cql);
            List<Record> res = neo4jUtil.executeCypherSql(cql);

            if (res.isEmpty()) throw new Exception("GraphService: getUserPreference: No user with id " + userId);

            Map<String, Object> record = res.get(0).asMap();
            List<String> genreLabels = convertLabelList(record.get(LABEL_GENRES), Collections.singletonList(LABEL_USER));
            return genreLabels;
        } catch (
                Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }

    }

    @Override
    public List<Genre> getUserPreferenceGenre(Integer userId) {
        return getUserPreferenceLabel(userId).stream().map(GraphService::labelToGenre).toList();
    }

    @Override
    public boolean updateUserPreference(Integer userId, List<String> remove, List<String> insert) {
        if (remove.isEmpty() && insert.isEmpty()) return true;
        try {
            String removeLabels = "`" + String.join("`:`", remove) + "`";
            String insertLabels = "`" + String.join("`:`", insert) + "`";

            String cql = "MATCH (e1: %s) WHERE e1.%s=%d ";
            cql = String.format(cql,
                    LABEL_USER,
                    PROP_USER_ID, userId);

            if (!remove.isEmpty()) {
                cql = cql + "REMOVE e1:%s ";
                cql = String.format(cql, removeLabels);
            }
            if (!insert.isEmpty()) {
                cql = cql + "SET e1:%s ";
                cql = String.format(cql, insertLabels);
            }

            log.info("updateUserPreference: {}", cql);
            neo4jUtil.executeCypherSql(cql);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Integer> getMoviesByGenre(Genre genre, int limit) {
        try {
            String cql = "MATCH (e1:%s:`%s`) "
                    + "RETURN e1.%s as %s "
                    + "LIMIT %d";
            cql = String.format(cql,
                    LABEL_MOVIE, genreToLabel(genre),
                    PROP_MOVIE_ID, PROP_MOVIE_ID,
                    limit);

            log.info("getMoviesByGenre: {}", cql);
            List<Record> res = neo4jUtil.executeCypherSql(cql);
            return res.stream().map(record -> record.get(PROP_MOVIE_ID).asInt()).toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Integer> getMoviesInDepth(Integer startMovieId, int depth, int limit) {
        try {
            String cql = "MATCH (e1:%s)-[*0..%d]-(e2:%s) "
                    + "WHERE e1.%s=%d "
                    + "RETURN e2.%s as %s "
                    + "LIMIT %d";
            cql = String.format(cql,
                    LABEL_MOVIE, depth, LABEL_MOVIE,
                    PROP_MOVIE_ID, startMovieId,
                    PROP_MOVIE_ID, PROP_MOVIE_ID,
                    limit);

            log.info("getMoviesInDepth: {}", cql);
            List<Record> res = neo4jUtil.executeCypherSql(cql);
            return res.stream().map(record -> record.get(PROP_MOVIE_ID).asInt()).toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

}
