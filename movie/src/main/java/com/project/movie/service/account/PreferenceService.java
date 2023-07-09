package com.project.movie.service.account;

import com.project.movie.domain.DO.User;
import com.project.movie.domain.VO.UserLoginVO;

public interface PreferenceService {
    User login(String email, String password);

    boolean register(UserLoginVO userLoginVO);
}
