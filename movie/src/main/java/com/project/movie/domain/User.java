package com.project.movie.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class User {
    private Long userId;

    private String password;

    private String email;

    private String userName;
}
