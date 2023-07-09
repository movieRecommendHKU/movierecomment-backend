package com.project.movie.service.movie.base.impl;

import com.project.movie.domain.DO.Genre;
import com.project.movie.mapper.movie.GenreMapper;
import com.project.movie.service.movie.base.GenreService;
//import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//import javax.annotation.Resource;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Qualifier
    GenreMapper genreMapper;

    @Override
    public List<Genre> getAllGenres() {
        return genreMapper.getAllGenres();
    }
}
