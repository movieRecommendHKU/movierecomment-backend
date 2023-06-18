package com.project.movie.domain.DO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class Rating {
    Integer ratingId;
    Integer userId;
    Integer movieId;
    Double rating;
    Date timestamp;
}
