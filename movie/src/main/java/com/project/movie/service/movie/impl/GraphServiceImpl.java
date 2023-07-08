package com.project.movie.service.movie.impl;

import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.User;
import com.project.movie.service.movie.GraphService;
import com.project.movie.utils.Neo4jUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GraphServiceImpl implements GraphService {
    @Resource
    Neo4jUtil neo4jUtil;

    @Override
    public boolean insertUser(User user) {
        try {
            String cql = "CREATE (e: %s {%s:%d})";
            cql = String.format(cql,
                    TAG_USER,
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
                    + "WHERE e1.%s=%d AND e2.%s=%d "
                    + "CREATE (e1)-[r:%s]->(e2)";
            cql = String.format(cql,
                    TAG_USER,
                    TAG_MOVIE,
                    PROP_USER_ID,
                    userId,
                    PROP_MOVIE_ID,
                    movieId,
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
                    + "WHERE e1.%s=%d AND e2.%s=%d "
                    + "DELETE r";
            cql = String.format(cql,
                    TAG_USER,
                    action,
                    TAG_MOVIE,
                    PROP_USER_ID,
                    userId,
                    PROP_MOVIE_ID,
                    movieId);
            log.info("deleteAction: {}", cql);
            neo4jUtil.executeCypherSql(cql);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
