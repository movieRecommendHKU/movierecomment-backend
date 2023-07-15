package com.project.movie.service.account;

import com.project.movie.domain.DO.User;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.domain.VO.UserLoginVO;

import java.util.Date;
import java.util.List;

public interface AccountService {
    User login(String email, String password);

    User register(UserLoginVO userLoginVO);

    List<User> getUsersByLastLogin(Date lastLogin);

}
