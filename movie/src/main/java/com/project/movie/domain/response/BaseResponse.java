//package com.project.movie.domain.response;
//
//import org.springframework.http.HttpStatus;
//
//import java.io.Serial;
//import java.util.HashMap;
//
//public class BaseResponse {
//
//    public BaseResponse(boolean status, Object content, String msg) {
//    }
//
//    public static BaseResponse success(String msg, Object content) {
//        return new BaseResponse(true, content, msg);
//    }
//
//    public static BaseResponse success(String msg) {
//        return new BaseResponse(true, null, msg);
//    }
//
//    public static BaseResponse error(String msg) {
//        return new BaseResponse(false, null, msg);
//    }
//}
package com.project.movie.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseResponse {
    boolean status;
    String msg;
    Object content;

    public BaseResponse() {}

    public BaseResponse(boolean status, Object content, String msg) {
    }

    public BaseResponse success(String msg, Object content) {
        return new BaseResponse(true, content, msg);
    }

    public BaseResponse success(String msg) {
        return new BaseResponse(true, null, msg);
    }

    public BaseResponse error(String msg) {
        return new BaseResponse(false, null, msg);
    }
}