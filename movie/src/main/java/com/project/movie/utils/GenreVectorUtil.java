package com.project.movie.utils;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.GenreScore;
import com.project.movie.service.movie.base.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
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

    private synchronized Integer getVectorSize() {
        initGenreMap();
        log.debug("GENRE_ID_TO_VECTOR_INDEX size {}", GENRE_ID_TO_VECTOR_INDEX.size());
        return GENRE_ID_TO_VECTOR_INDEX.size();
    }

    public List<Double> initGenreVector() {
        List<Double> vector = new ArrayList<>();
        for (int i = 0; i < getVectorSize(); i++) {
            vector.add(0.);
        }
        return vector;
    }

    public synchronized Integer getIndexByGenre(Genre genre) {
        initGenreMap();
        return GENRE_ID_TO_VECTOR_INDEX.get(genre.getGenreName());
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

    public void normalize(List<GenreScore> genreScoreList) {
        double maxScore = genreScoreList.stream()
                .mapToDouble(GenreScore::getScore)
                .max()
                .orElse(0);
        double minScore = genreScoreList.stream()
                .mapToDouble(GenreScore::getScore)
                .min()
                .orElse(0);
        double delta = maxScore - minScore;
        final double minMax = delta == 0 ? 1 : delta;
        genreScoreList.forEach(genreScore -> genreScore.setScore((genreScore.getScore() - minScore) / minMax));
    }

    public List<Double> normalizeVector(List<Double> vector) {
        double maxScore = vector.stream().mapToDouble(Double::doubleValue).max().orElse(0.);
        double minScore = vector.stream().mapToDouble(Double::doubleValue).min().orElse(0.);
        double delta = maxScore - minScore;
        final double minMax = delta == 0 ? 1 : delta;
        return vector.stream().map(score -> (score - minScore) / minMax).toList();
    }
}
