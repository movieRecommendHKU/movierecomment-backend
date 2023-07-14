package com.project.movie.domain.DO;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class User {
    private Integer userId;

    private String password;

    private String email;

    private String userName;

    private Date lastLogin;
}
