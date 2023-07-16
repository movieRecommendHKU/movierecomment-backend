package com.project.movie.service.account;

import com.project.movie.controller.account.AccountController;
import com.project.movie.domain.DO.User;
import com.project.movie.mapper.account.AccountMapper;
import com.project.movie.service.movie.kg.GraphService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountServiceTest {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    GraphService graphService;

    @Test
    public void register() {
        User user = new User().setUserName("celiaf").setEmail("celiaf@qq.com").setPassword("pswd");
        accountMapper.insertAccount(user);
        boolean kgRes = graphService.insertUser(user);
        Assertions.assertNotNull(user.getUserId());
        Assertions.assertTrue(kgRes);
    }

    @Test
    public void login() {
        User user = accountMapper.findByEmail("celiaf@qq.com");
        accountMapper.login(user);
    }
}
