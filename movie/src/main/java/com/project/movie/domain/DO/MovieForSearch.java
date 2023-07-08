package com.project.movie.domain.DO;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MovieForSearch {
    private Integer movieId;
    private String movieName;
    private String overview;
    private String directorName;
    private String producer;
    private Double rating;
    private String releaseDate;
    private List<String> castNames;
    private List<String> keyWords;
    private List<String> genres;
    //private dense_vector wordVectors;
    //private List<Double> sentenceVectors;
}
