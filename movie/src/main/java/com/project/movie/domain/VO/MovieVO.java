package com.project.movie.domain.VO;

import com.project.movie.domain.DO.Cast;
import com.project.movie.domain.DO.Director;
import com.project.movie.domain.DO.Producer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/*
	Movie + Cast
 */
@Data
@Accessors(chain = true)
public class MovieVO {
	private Integer movieId;
	private String movieName;
	private String overview;
	private Double rating;
	private String releaseDate;
	private Double popularity;
	private String posterPath;
	private Integer voteCount;

	private Director director;
	private Producer producer;
	private List<Cast> casts;
}
