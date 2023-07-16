package com.project.movie.domain.DO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class MovieSimilarity {
	private Integer id;
	private Integer movieId;
	private Integer similarId;
	private Double similarity;
}
