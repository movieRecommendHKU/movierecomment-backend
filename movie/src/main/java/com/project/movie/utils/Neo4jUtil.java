package com.project.movie.utils;

import com.project.movie.config.Common;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
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

    public List<Record> executeCypherSql(String cypherSql) {
        List<Record> result = null;
        try (Session session = neo4jDriver.session()) {
            Transaction tx = session.beginTransaction();
            result = tx.run(cypherSql).list();
            log.info("executeCypherSql result: {}", result);
            tx.commit();
        } catch (Exception e) {
            log.warn("executeCypherSql:{}", e.getMessage());
        }
        return result;
    }
}
