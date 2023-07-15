package com.project.movie.mapper.account;

import com.project.movie.domain.DO.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountMapper {

    User findByEmail(@Param("email") String email);

    void insertAccount(User user);

    void login(User user);

    List<User> getUsersByLastLogin(Date LastLogin);

}
