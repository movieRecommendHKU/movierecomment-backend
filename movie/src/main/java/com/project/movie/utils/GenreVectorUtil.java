package com.project.movie.utils;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.GenreScore;
import com.project.movie.service.movie.base.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GenreVectorUtil {

    @Autowired
    private GenreService genreService;

    private volatile static Map<String, Integer> GENRE_ID_TO_VECTOR_INDEX;

    public synchronized List<Double> convertGenreVector(List<GenreScore> genreScoreList) {
        initGenreMap();
        List<Double> vector = new ArrayList<>(GENRE_ID_TO_VECTOR_INDEX.size());
        genreScoreList.forEach(genreScore -> {
            int index = GENRE_ID_TO_VECTOR_INDEX.get(genreScore.getGenreName());
            vector.set(index, genreScore.getScore());
        });
        return vector;
    }

    private synchronized void initGenreMap() {
        if (GENRE_ID_TO_VECTOR_INDEX == null) {
            GENRE_ID_TO_VECTOR_INDEX = new HashMap<>();
            List<Genre> genreList = genreService.getAllGenres();
            genreList.sort(Comparator.comparingInt(Genre::getGenreId));
            int index = 0;
            for (Genre genre : genreList) {
                GENRE_ID_TO_VECTOR_INDEX.put(genre.getGenreName(), index);
                index += 1;
            }
        }
    }
}
