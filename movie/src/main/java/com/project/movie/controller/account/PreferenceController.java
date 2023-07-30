package com.project.movie.controller.account;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.Preference;
import com.project.movie.domain.DTO.UserSimilarityInfo;
import com.project.movie.domain.VO.PreferenceVO;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.account.AccountService;
import com.project.movie.service.account.PreferenceService;
import com.project.movie.service.movie.base.GenreService;
import com.project.movie.service.recommend.RecommendService;
import com.project.movie.service.search.SearchSimilarUserService;
import com.project.movie.service.search.UserSearchDataService;
import com.project.movie.utils.GenreVectorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/preference")
@Slf4j
public class PreferenceController {
    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private SearchSimilarUserService searchSimilarUserService;

    @Autowired
    private UserSearchDataService userSearchDataService;

    @Autowired
    private GenreVectorUtil genreVectorUtil;

    @Autowired
    private RecommendService recommendService;


    @GetMapping("/get_preference")
    public BaseResponse getPreference(@RequestParam Integer userId) {
        return new BaseResponse()
                .setStatus(true)
                .setMsg("Get user preference successfully.")
                .setContent(preferenceService.getUserPreference(userId));
    }

    @PostMapping("/batch_insert")
    public BaseResponse batchInsert(@RequestBody Preference preference) {
        boolean res = preferenceService.batchInsert(preference);
        try {
            List<Double> vector = searchSimilarUserService.getVectorByUserId(preference.getUserId());
            for (Genre genre : preference.getGenres()) {
                int index = genreVectorUtil.getIndexByGenre(genre);
                vector.set(index, vector.get(index) + 1);
            }
            vector = genreVectorUtil.normalizeVector(vector);
            userSearchDataService.addUserToElasticSearch(new UserSimilarityInfo(preference.getUserId(), vector));
        } catch (Exception e) {
            log.error("Insert user preference failed. error: {}", e.getMessage());
        }
        return res ?
                new BaseResponse()
                        .setStatus(true)
                        .setMsg("Batch insert user preference successfully.") :
                new BaseResponse()
                        .setStatus(false)
                        .setMsg("Batch insert user preference failed.");
    }

    @PostMapping("/batch_delete")
    public BaseResponse batchDelete(@RequestBody Preference preference) {
        boolean res = preferenceService.batchDelete(preference);
        try {
            List<Double> vector = searchSimilarUserService.getVectorByUserId(preference.getUserId());
            for (Genre genre : preference.getGenres()) {
                int index = genreVectorUtil.getIndexByGenre(genre);
                vector.set(index, vector.get(index) - 1);
            }
            vector = genreVectorUtil.normalizeVector(vector);
            userSearchDataService.addUserToElasticSearch(new UserSimilarityInfo(preference.getUserId(), vector));
        } catch (Exception e) {
            log.error("Delete user preference failed. error: {}", e.getMessage());
        }
        return res ?
                new BaseResponse()
                        .setStatus(true)
                        .setMsg("Batch delete user preference successfully.") :
                new BaseResponse()
                        .setStatus(false)
                        .setMsg("Batch delete user preference failed.");
    }

    @PostMapping("/modify")
    public BaseResponse modify(@RequestBody PreferenceVO preferenceVO) {
        Integer userId = preferenceVO.getUserId();
        boolean addRes = preferenceVO.getAddGenres().isEmpty() ||
                preferenceService.batchInsert(new Preference()
                        .setGenres(preferenceVO.getAddGenres())
                        .setUserId(userId));
        boolean delRes = preferenceVO.getDeleteGenres().isEmpty() ||
                preferenceService.batchDelete(new Preference()
                        .setGenres(preferenceVO.getDeleteGenres())
                        .setUserId(userId));
        boolean res = addRes && delRes;


        List<Double> vector = searchSimilarUserService.getVectorByUserId(preferenceVO.getUserId());
        vector = vector.isEmpty() ? genreVectorUtil.initGenreVector() : vector;
        for (Genre genre : preferenceVO.getAddGenres()) {
            int index = genreVectorUtil.getIndexByGenre(genre);
            vector.set(index, vector.get(index) + 1);
        }
        for (Genre genre : preferenceVO.getDeleteGenres()) {
            int index = genreVectorUtil.getIndexByGenre(genre);
            vector.set(index, vector.get(index) - 1);
        }
        vector = genreVectorUtil.normalizeVector(vector);
        try {
            userSearchDataService.addUserToElasticSearch(new UserSimilarityInfo(preferenceVO.getUserId(), vector));
        } catch (Exception e) {
            log.error("Modify user preference vector failed. error: {}", e.getMessage());
        }

        List<Integer> recommendations = recommendService.getAllMovies(userId);
        String[] recommendationsArray = recommendations.stream().map(Object::toString).toArray(String[]::new);
        String recommendLog = String.join(",", recommendationsArray);
        res = res && recommendService.insetRecommendLog(userId, String.join(",", recommendLog));

        return res ?
                new BaseResponse()
                        .setStatus(true)
                        .setMsg("Modify user preference successfully.") :
                new BaseResponse()
                        .setStatus(false)
                        .setMsg("Modify user preference failed.");
    }

    @GetMapping("/get_all_genres")
    public BaseResponse listGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return genres != null ?
                new BaseResponse().setStatus(true).setMsg("Get genres successfully.").setContent(genres) :
                new BaseResponse().setStatus(false).setMsg("Get genres failed.");
    }

}
