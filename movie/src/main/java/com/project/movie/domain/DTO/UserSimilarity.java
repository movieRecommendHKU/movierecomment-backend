package com.project.movie.domain.DTO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class UserSimilarity {
	private Integer userId;
	private Double similarity;
}
