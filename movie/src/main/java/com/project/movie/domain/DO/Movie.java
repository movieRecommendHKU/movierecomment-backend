package com.project.movie.domain.DO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class Movie {
    private Integer movieId;
    private String movieName;
    private String overview;
    private Integer director; // directorId
    private Integer producer; // producerId
    private Double rating;
    private String releaseDate;
    private Double popularity;
    private String posterPath;
    private Integer voteCount;
}
