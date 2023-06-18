package com.project.movie.controller;

import com.project.movie.domain.AjaxResult;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public AjaxResult login(@RequestParam String email, @RequestParam String password) {
        return accountService.login(email, password);
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody UserLoginVO vo) {
        return accountService.register(vo);
    }
}
