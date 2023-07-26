package com.project.movie.service.search.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.project.movie.domain.DO.Movie;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.service.movie.base.MovieService;
import com.project.movie.service.movie.base.impl.MovieServiceImpl;
import com.project.movie.service.search.SearchMoviesService;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.project.movie.domain.flask.Flask.KEYWORDS_SEARCH;
import static com.project.movie.domain.flask.Flask.SENTENCES_SEARCH;

@Service
public class SearchMoviesServiceImpl implements SearchMoviesService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private MovieServiceImpl movieService;

    @Override
    public List<MovieVO> searchByKeywords(String input_words, Integer k) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("string_keywords", input_words);
        jsonObject.put("k", k);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(jsonObject), headers);

        ResponseEntity<String> stringResponseEntity = null;
        try {
            stringResponseEntity = restTemplate.postForEntity(KEYWORDS_SEARCH, formEntity, String.class);
            System.out.println("ResponseEntity----" + stringResponseEntity);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        String body = null;
        if (stringResponseEntity != null) {
            body = stringResponseEntity.getBody();
        }

        List<Integer> movie_Ids = JSONObject.parseArray(body, Integer.class);
        System.out.println(movie_Ids);
        List<MovieVO> movies = movieService.batchAssembleMovie(movie_Ids).stream()
                .map(movie -> movieService.assembleMovieVO(movie)).toList();

        return movies;
    }

    @Override
    public List<MovieVO> searchBySentences(String input_sentences, Integer k) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("string_sentences", input_sentences);
        jsonObject.put("k", k);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(jsonObject), headers);

        ResponseEntity<String> stringResponseEntity = null;
        try {
            stringResponseEntity = restTemplate.postForEntity(SENTENCES_SEARCH, formEntity, String.class);
            System.out.println("ResponseEntity----" + stringResponseEntity);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        String body = null;
        if (stringResponseEntity != null) {
            body = stringResponseEntity.getBody();
        }

        List<Integer> movie_Ids = JSONObject.parseArray(body, Integer.class);
        System.out.println(movie_Ids);
        List<MovieVO> movies = movieService.batchAssembleMovie(movie_Ids).stream()
                .map(movie -> movieService.assembleMovieVO(movie)).toList();

        return movies;
    }
}
