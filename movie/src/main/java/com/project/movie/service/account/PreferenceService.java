package com.project.movie.service.account;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.Preference;

import java.util.List;

public interface PreferenceService {
    List<Genre> getUserPreference(Integer userId);

    boolean batchInsert(Preference preference);

    boolean batchDelete(Preference preference);
}
