package com.project.movie.service.movie.impl;

import com.project.movie.domain.DO.Genre;
import com.project.movie.mapper.movie.GenreMapper;
import com.project.movie.service.movie.GenreService;
//import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Resource
    GenreMapper genreMapper;

    @Override
    public List<Genre> getAllGenres() {
        return genreMapper.getAllGenres();
    }
}
