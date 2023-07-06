package com.project.movie.controller.account;

import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;


    final private static String EMPTY_ACCOUNT = "账号不存在！";

    final private static String PASSWORD_ERROR = "账号或密码错误！";

    final private static String ACCOUNT_EXIST = "账号已存在！";


    @PostMapping("/login")
    public BaseResponse login(@RequestParam String email, @RequestParam String password) {
        return accountService.login(email, password);
    }

    @PostMapping("/register")
    public BaseResponse register(@RequestBody UserLoginVO vo) {
        return accountService.register(vo);
    }
}
