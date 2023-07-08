package com.project.movie.domain.DO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class Cast {
    private Integer castId;
    private String castName;
    private Integer gender;
    private String profilePath;
}
