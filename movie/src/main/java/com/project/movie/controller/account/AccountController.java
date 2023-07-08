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
                return BaseResponse.success("Login success!", user);
            } else {
                return BaseResponse.error(PASSWORD_ERROR);
            }
        } else {
            return BaseResponse.error(EMPTY_ACCOUNT);
        }
    }

    @PostMapping("/register")
    public BaseResponse register(@RequestBody UserLoginVO vo) {
        if(accountService.register(vo))
            return BaseResponse.success("Sign Up success!");
        else
            return BaseResponse.error(ACCOUNT_EXIST);
    }
}
