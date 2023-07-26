package com.project.movie.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Accessors(chain = true)
public class UserSimilarityInfo {
    private Integer userId;
    private List<Double> vector;
}
