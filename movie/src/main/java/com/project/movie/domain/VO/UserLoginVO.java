package com.project.movie.domain.VO;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLoginVO {
    private Integer userId;

    private String userName;

    private String email;

    private String password;

}
