package com.project.movie.domain.response;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.HashMap;

public class BaseResponse {

    BaseResponse(boolean status, Object content, String msg) {
    }

    public static BaseResponse success(String msg, Object content) {
        return new BaseResponse(true, content, msg);
    }

    public static BaseResponse error(String msg) {
        return new BaseResponse(false, null, msg);
    }
}
