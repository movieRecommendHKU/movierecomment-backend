package com.project.movie.domain.DO;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Genre {
    private Integer genreId;
    private String genreName;
}
