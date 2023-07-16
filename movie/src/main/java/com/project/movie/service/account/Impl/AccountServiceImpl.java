package com.project.movie.service.account.Impl;

import com.project.movie.domain.DO.User;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.mapper.account.AccountMapper;
import com.project.movie.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    @Qualifier("accountMapper")
    private AccountMapper accountMapper;

    @Override
    public User login(String email, String password) {
        User user = accountMapper.findByEmail(email);
        if (user != null) {
            accountMapper.login(user);
        }
        return user;
    }

    @Override
    public User register(UserLoginVO loginVO) {
        User user = accountMapper.findByEmail(loginVO.getEmail());
        if (user != null)
            return null;
        User newUser = new User().setUserName(loginVO.getUserName())
                .setPassword(loginVO.getPassword())
                .setEmail(loginVO.getEmail());
        accountMapper.insertAccount(newUser);
        return newUser;
    }

    @Override
    public List<User> getUsersByLastLogin(Date lastLogin) {
        return accountMapper.getUsersByLastLogin(lastLogin);
    }
}
