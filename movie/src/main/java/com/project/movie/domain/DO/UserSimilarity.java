package com.project.movie.domain.DO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class UserSimilarity {
	private Integer id;
	private Integer userId;
	private Integer similarUserId;
	private Double similarity;
}
