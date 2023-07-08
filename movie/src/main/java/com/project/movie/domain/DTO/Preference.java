package com.project.movie.domain.DTO;

import com.project.movie.domain.DO.Genre;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Preference {
    Integer userId;
    List<Genre> genres;
}
