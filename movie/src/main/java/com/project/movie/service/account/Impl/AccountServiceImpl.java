package com.project.movie.service.account.Impl;

import com.project.movie.domain.response.BaseResponse;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.mapper.account.AccountMapper;
import com.project.movie.service.account.AccountService;
//import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    final private static String EMPTY_ACCOUNT = "Account not exist！";

    final private static String PASSWORD_ERROR = "Wrong account or password！";

    final private static String ACCOUNT_EXIST = "Account existed！";

    @Override
    public BaseResponse login(String email, String password) {
        User user = accountMapper.findByEmail(email);
        if (user == null)
            return new BaseResponse().setStatus(false).setMsg(EMPTY_ACCOUNT);
        if (!user.getPassword().equals(password))
            return new BaseResponse().setStatus(false).setMsg(PASSWORD_ERROR);
        else {
            UserLoginVO loginVO = new UserLoginVO()
                    .setUserId(user.getUserId())
                    .setUserName(user.getUserName())
                    .setEmail(user.getEmail());
            return new BaseResponse().setStatus(true).setMsg("Login successfully!").setContent(loginVO);
        }
    }

    @Override
    public BaseResponse register(UserLoginVO loginVO) {
        User user = accountMapper.findByEmail(loginVO.getEmail());
        if (user != null)
            return new BaseResponse().setStatus(false).setMsg(ACCOUNT_EXIST);
        User newUser = new User().setUserName(loginVO.getUserName())
                .setPassword(loginVO.getPassword())
                .setEmail(loginVO.getEmail());
        accountMapper.insertAccount(newUser);
        return new BaseResponse().setStatus(true).setMsg("Register successfully").setContent(loginVO);
    }
}
