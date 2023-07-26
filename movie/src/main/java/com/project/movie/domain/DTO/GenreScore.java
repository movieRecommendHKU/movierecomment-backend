package com.project.movie.domain.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenreScore {
    private Integer genreId;
    private String genreName;
    private double score;
}
