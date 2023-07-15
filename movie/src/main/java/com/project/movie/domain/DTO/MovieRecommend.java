package com.project.movie.domain.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MovieRecommend {
    Integer movieId;
    Double weight;
    Integer count;
}
