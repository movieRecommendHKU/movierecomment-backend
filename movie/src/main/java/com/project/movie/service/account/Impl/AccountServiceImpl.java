package com.project.movie.service.account.Impl;

import com.project.movie.domain.response.BaseResponse;
import com.project.movie.domain.DO.User;
import com.project.movie.domain.VO.UserLoginVO;
import com.project.movie.mapper.account.AccountMapper;
import com.project.movie.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {
    @Qualifier
    private AccountMapper accountMapper;

    @Override
    public User login(String email, String password) {
        return accountMapper.findByEmail(email);
    }

    @Override
    public boolean register(UserLoginVO loginVO) {
        User user = accountMapper.findByEmail(loginVO.getEmail());
        if(user != null)
            return false;
        User newUser = new User().setUserName(loginVO.getUserName())
                .setPassword(loginVO.getPassword())
                .setEmail(loginVO.getEmail());
        accountMapper.insertAccount(newUser);
        return true;
    }
}
