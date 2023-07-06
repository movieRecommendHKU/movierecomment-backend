package com.project.movie.service.movie.impl;

import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.DO.User;
import com.project.movie.service.movie.GraphService;
import com.project.movie.utils.Neo4jUtil;
import jakarta.annotation.Resource;

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
            neo4jUtil.executeCypherSql(cql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean takeAction(User user, Movie movie, String action) {
        try {
            String cql = "MATCH (e1: %s), (e2: %s) "
                    + "WHERE e1.%s=%d AND e2.%s=%d"
                    + "CREATE (e1)-[r:%s]->(e2)";
            cql = String.format(cql,
                    TAG_USER,
                    TAG_MOVIE,
                    user.getUserId(),
                    movie.getMovieId(),
                    action);
            neo4jUtil.executeCypherSql(cql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAction(User user, Movie movie, String action) {
        try {
            String cql = "MATCH (e1: %s)-[r:%s]->(e2: %s) "
                    + "WHERE e1.%s=%d AND e2.%s=%d"
                    + "DELETE r";
            cql = String.format(cql,
                    TAG_USER,
                    action,
                    TAG_MOVIE,
                    user.getUserId(),
                    movie.getMovieId());
            neo4jUtil.executeCypherSql(cql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
