package com.project.movie.service.account;

import com.project.movie.domain.response.BaseResponse;
import com.project.movie.domain.VO.UserLoginVO;

public interface AccountService {
    BaseResponse login(String email, String password);

    BaseResponse register(UserLoginVO userLoginVO);
}
