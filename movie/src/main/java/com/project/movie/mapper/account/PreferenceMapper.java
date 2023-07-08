package com.project.movie.mapper.account;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.Preference;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceMapper {

    List<Genre> getUserPreference(Integer userId);

    int batchInsert(Preference preference);

    int batchDelete(Preference preference);
}
