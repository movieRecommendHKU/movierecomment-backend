package com.project.movie.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.HashMap;

@Data
@Accessors(chain = true)
public class BaseResponse {
    boolean status;
    String msg;
    Object content;
}
