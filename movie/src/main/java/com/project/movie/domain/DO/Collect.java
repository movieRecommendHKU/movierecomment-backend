package com.project.movie.domain.DO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class Collect {
    Integer collectId;
    Integer userId;
    Integer movieId;
}
