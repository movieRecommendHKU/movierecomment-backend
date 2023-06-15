package com.project.movie.service;

import com.project.movie.domain.AjaxResult;
import com.project.movie.domain.User;
import com.project.movie.domain.VO.UserLoginVO;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    AjaxResult login(String email, String password);

    AjaxResult register(UserLoginVO userLoginVO);
}
