package com.project.movie.controller.account;

import com.project.movie.domain.DO.Genre;
import com.project.movie.domain.DTO.Preference;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.account.AccountService;
import com.project.movie.service.account.PreferenceService;
import com.project.movie.service.movie.base.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preference")
public class PreferenceController {
    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private GenreService genreService;


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
        return res ?
                new BaseResponse()
                        .setStatus(true)
                        .setMsg("Batch delete user preference successfully.") :
                new BaseResponse()
                        .setStatus(false)
                        .setMsg("Batch delete user preference failed.");
    }

    @GetMapping("/get_all_genres")
    public BaseResponse listGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return genres != null ?
                new BaseResponse().setStatus(true).setMsg("Get genres successfully.").setContent(genres) :
                new BaseResponse().setStatus(false).setMsg("Get genres failed.");
    }

}
