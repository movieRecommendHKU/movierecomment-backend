package com.project.movie.mapper.director;

import com.project.movie.domain.DO.Director;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorMapper {

	Director getDirectorByMovieId(int movieId);
}
