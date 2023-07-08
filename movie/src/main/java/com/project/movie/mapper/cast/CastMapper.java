package com.project.movie.mapper.cast;

import com.project.movie.domain.DO.Cast;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastMapper {

	List<Cast> getCastsByMovieId(int movieId);
}
