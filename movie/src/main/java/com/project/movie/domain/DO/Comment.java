package com.project.movie.domain.DO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class Comment {
    Integer commentId;
    Integer userId;
    Integer movieId;
    String content;
    Date timestamp;
}
