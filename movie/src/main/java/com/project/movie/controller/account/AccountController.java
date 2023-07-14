package com.project.movie.controller.account;

import com.project.movie.domain.DO.User;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.account.AccountService;
import com.project.movie.service.movie.kg.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private GraphService graphService;
    final private static String ACCOUNT_EXIST = "Account exist！";
    final private static String EMPTY_ACCOUNT = "Account not exist！";
    final private static String PASSWORD_ERROR = "Wrong account or password！";

    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserLoginVO vo) {
        User user = accountService.login(vo.getEmail(), vo.getPassword());

        if (user != null) {
            if (user.getPassword().equals(vo.getPassword())) {
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
        User user = accountService.register(vo);
        if (user != null) {
            graphService.insertUser(user);
            return new BaseResponse().setStatus(true).setMsg("Sign Up success!");
        } else {
            return new BaseResponse().setStatus(false).setMsg(ACCOUNT_EXIST);
        }
    }
}
