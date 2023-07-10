package com.project.movie.service.account.Impl;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.Preference;
import com.project.movie.mapper.account.PreferenceMapper;
import com.project.movie.service.account.PreferenceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

//import javax.annotation.Resource;

@Service
@Slf4j
public class PreferenceServiceImpl implements PreferenceService {
    @Resource
    private PreferenceMapper preferenceMapper;

    @Override
    public List<Genre> getUserPreference(Integer userId) {
        return preferenceMapper.getUserPreference(userId);
    }

    @Override
    public boolean batchInsert(Preference preference) {
        log.info("Batch insert genres id list:{}",
                preference.getGenres().stream().map(Genre::getGenreId).toList());
        return preference.getGenres().size() == preferenceMapper.batchInsert(preference);
    }

    @Override
    public boolean batchDelete(Preference preference) {
        log.info("Batch delete genres id list:{}",
                preference.getGenres().stream().map(Genre::getGenreId).toList());
        return preference.getGenres().size() == preferenceMapper.batchDelete(preference);
    }
}
