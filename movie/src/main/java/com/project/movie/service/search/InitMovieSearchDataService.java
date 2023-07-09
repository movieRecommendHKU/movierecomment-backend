package com.project.movie.service.search;

import com.project.movie.domain.DO.MovieForSearch;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public interface InitMovieSearchDataService {

    List<Map<String,String>> readXlsx(File file) throws Exception;

    List<MovieForSearch> getMovieForSearchList(File file) throws Exception;

    Integer addMovieToElasticSearch(String contentPath) throws Exception;

}
