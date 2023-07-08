package com.project.movie.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseResponse {
    boolean status;
    String msg;
    Object content;
}
