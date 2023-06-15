package com.project.movie.service.Impl;

import com.project.movie.domain.AjaxResult;
import com.project.movie.domain.User;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.mapper.AccountMapper;
import com.project.movie.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    final private static String EMPTY_ACCOUNT = "账号不存在！";

    final private static String PASSWORD_ERROR = "账号或密码错误！";

    final private static String ACCOUNT_EXIST = "账号已存在！";

    @Override
    public AjaxResult login(String email, String password) {
        User user = accountMapper.findByEmail(email);
        if (user == null)
            return AjaxResult.error(EMPTY_ACCOUNT);
        if (!user.getPassword().equals(password))
            return AjaxResult.error(PASSWORD_ERROR);
        else {
            UserLoginVO loginVO = new UserLoginVO()
                    .setUserId(user.getUserId())
                    .setUserName(user.getUserName())
                    .setEmail(user.getEmail());
            return AjaxResult.success("登录成功！", loginVO);
        }
    }

    @Override
    public AjaxResult register(UserLoginVO loginVO) {
        User user = accountMapper.findByEmail(loginVO.getEmail());
        if(user != null)
            return AjaxResult.error(ACCOUNT_EXIST);
        User newUser = new User().setUserName(loginVO.getUserName())
                .setPassword(loginVO.getPassword())
                .setEmail(loginVO.getEmail());
        accountMapper.insertAccount(newUser);
        return AjaxResult.success("注册成功", loginVO);
    }
}
