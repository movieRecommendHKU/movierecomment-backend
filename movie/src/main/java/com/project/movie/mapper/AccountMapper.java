package com.project.movie.mapper;

import com.project.movie.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {

    User findByEmail(@Param("email") String email);

    void insertAccount(User user);

}
