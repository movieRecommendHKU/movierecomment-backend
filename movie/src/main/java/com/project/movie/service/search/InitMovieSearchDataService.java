package com.project.movie.service.search;

import com.project.movie.domain.DO.MovieForSearch;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public interface InitMovieSearchDataService {

    List<Map<String,String>> readXlsx(File file) throws Exception;

    Map<Integer,MovieForSearch> getMovieForSearchMap(File file) throws Exception;

    Integer addMovieToElasticSearch(String contentPath) throws Exception;

//    void setDirectorNameForMovie(File file, Map<Integer, MovieForSearch> movieForSearchMap) throws Exception;

    void setVectorForMovie(File file, Map<Integer,MovieForSearch> movieForSearchMap, Integer vectorType) throws Exception;

}
