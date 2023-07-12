package com.project.movie.domain.DO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieForSearch {
    private Integer movieId;
    private String movieName;
    private String overview;
    private String directorName;
    private List<String> producerNames;
    private Double rating;
    private String releaseDate;
    private List<String> castNames;
    private List<String> keyWords;
    private List<String> genres;
    private List<Double> wordVectors;
    private List<Double> sentenceVectors;
}
