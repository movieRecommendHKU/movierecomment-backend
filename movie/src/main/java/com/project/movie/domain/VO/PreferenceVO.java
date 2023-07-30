package com.project.movie.domain.VO;

import com.project.movie.domain.DO.Genre;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PreferenceVO {
    Integer userId;
    List<Genre> addGenres;
    List<Genre> deleteGenres;
}
