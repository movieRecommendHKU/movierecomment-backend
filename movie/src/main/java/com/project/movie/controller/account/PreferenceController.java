package com.project.movie.controller.account;

import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preference")
public class PreferenceController {
    @Autowired
    private AccountService accountService;


    @PostMapping("/get_preference")
    public BaseResponse getPreference(@RequestParam Integer userId) {
        return null;
    }

}
