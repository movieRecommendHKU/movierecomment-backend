package com.project.movie.service.search.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.movie.domain.DTO.UserSimilarity;
import com.project.movie.service.search.SearchSimilarUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.project.movie.domain.flask.Flask.USER_SEARCH;

@Service
@Slf4j
public class SearchSimilarUserServiceImpl implements SearchSimilarUserService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private ElasticsearchClient esClient;

    @Override
    public List<Double> getVectorByUserId(Integer userId) {
        try {
            GetResponse<ObjectNode> response = esClient.get(g -> g
                            .index("user_es_data")
                            .id(String.valueOf(userId)),
                    ObjectNode.class
            );
            List<Double> vector = new ArrayList<>();
            if (response.source() != null) {
                vector = JSONObject.parseArray(response.source().get("vector").toString(), Double.class);
            }
            return vector;
        } catch (IOException e) {
            log.error("getVectorByUserId failed, userId {}, error: {}", userId, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<UserSimilarity> searchByUserSimilarity(Integer userId, Integer k) {
        // 输入UserSimilarityInfo（包括userId和similarity）和k（取多少个相似的）来获得结果
        // 返回对应相似的userId数组
        RestTemplate restTemplate = restTemplateBuilder.build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("vector", getVectorByUserId(userId));
        jsonObject.put("k", k);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(jsonObject), headers);

        ResponseEntity<String> stringResponseEntity = null;
        try {
            stringResponseEntity = restTemplate.postForEntity(USER_SEARCH, formEntity, String.class);
            System.out.println("ResponseEntity----" + stringResponseEntity);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        String body = null;
        if (stringResponseEntity != null) {
            body = stringResponseEntity.getBody();
        }

        List<UserSimilarity> user_similarity = JSONObject.parseArray(body, UserSimilarity.class);
        System.out.println(user_similarity);

        return user_similarity;

    }

}
