package com.project.movie.mapper.movie;

import com.project.movie.domain.DO.Genre;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreMapper {

    List<Genre> getAllGenres();

}
