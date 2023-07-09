package com.project.movie.controller.account;

import com.project.movie.domain.DO.User;
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

    final private static String ACCOUNT_EXIST = "Account exist！";
    final private static String EMPTY_ACCOUNT = "Account not exist！";
    final private static String PASSWORD_ERROR = "Wrong account or password！";

    @PostMapping("/login")
    public BaseResponse login(@RequestParam String email, @RequestParam String password) {
        User user =  accountService.login(email, password);
        if(user != null) {
            if(user.getPassword().equals(password)) {
                return new BaseResponse().setStatus(true).setMsg("Login success").setContent(user);
            } else {
                return new BaseResponse().setStatus(false).setMsg(PASSWORD_ERROR);
            }
        } else {
            return new BaseResponse().setStatus(false).setMsg(EMPTY_ACCOUNT);
        }
    }

    @PostMapping("/register")
    public BaseResponse register(@RequestBody UserLoginVO vo) {
        if(accountService.register(vo))
            return new BaseResponse().setStatus(true).setMsg("Sign Up success!");
        else
            return new BaseResponse().setStatus(false).setMsg(ACCOUNT_EXIST);
    }
}
