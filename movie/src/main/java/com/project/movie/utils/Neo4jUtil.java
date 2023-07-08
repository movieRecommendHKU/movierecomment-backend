package com.project.movie.utils;

import com.project.movie.config.Common;
import org.neo4j.driver.Driver;
import org.neo4j.driver.*;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Neo4jUtil {

    private final Driver neo4jDriver;

    @Autowired
    public Neo4jUtil(Driver neo4jDriver) {
        this.neo4jDriver = neo4jDriver;
    }

    public boolean connect() {
        try (Session session = neo4jDriver.session()) {
            System.out.println("连接成功：" + session.isOpen());
            return session.isOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Result executeCypherSql(String cypherSql) {
        Result result = null;
        try (Session session = neo4jDriver.session()) {
            System.out.println(cypherSql);
            result = session.run(cypherSql);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
}
