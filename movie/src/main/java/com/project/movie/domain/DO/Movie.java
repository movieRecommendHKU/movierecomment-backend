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
    private Integer director; // 暂时先将导演id存在数据库表中
    private String producer;
    private Double rating;
    private String releaseDate;
    private Double popularity;
    private String posterPath;
    private Integer voteCount;
}
