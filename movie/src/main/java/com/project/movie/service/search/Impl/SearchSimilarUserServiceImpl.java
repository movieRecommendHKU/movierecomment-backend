package com.project.movie.service.search.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.movie.domain.DO.UserSimilarityInfo;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.service.search.SearchSimilarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SearchSimilarUserServiceImpl implements SearchSimilarUserService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Integer> searchByUserSimilarity(UserSimilarityInfo userSimilarityInfo, Integer k) {
        // 输入UserSimilarityInfo（包括userId和similarity）和k（取多少个相似的）来获得结果
        // 返回对应相似的userId数组
        RestTemplate restTemplate = restTemplateBuilder.build();
        String URL = "http://127.0.0.1:5000/SearchByUserSimilarity";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("similarity", userSimilarityInfo.getSimilarity());
        jsonObject.put("k", k);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");

        HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(jsonObject), headers);

        ResponseEntity<String> stringResponseEntity = null;
        try {
            stringResponseEntity = restTemplate.postForEntity(URL, formEntity, String.class);
            System.out.println("ResponseEntity----"+stringResponseEntity);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        String body = null;
        if (stringResponseEntity != null) {
            body = stringResponseEntity.getBody();
        }

        List<Integer> user_Ids = JSONObject.parseArray(body, Integer.class);
        System.out.println(user_Ids);

        return user_Ids;

    }
}
