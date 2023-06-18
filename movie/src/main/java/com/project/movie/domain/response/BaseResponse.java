package com.project.movie.domain.response;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.HashMap;

public class BaseResponse extends HashMap<String, Object>
{
    @Serial
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 BaseResponse 对象，使其表示一个空消息。
     */
    public BaseResponse()
    {
    }

    /**
     * 初始化一个新创建的 BaseResponse 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public BaseResponse(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 BaseResponse 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public BaseResponse(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
//        if (StringUtils.isNotNull(data))
//        {
        super.put(DATA_TAG, data);
//        }
    }

    public BaseResponse(HttpStatus error, String msg, Object data){
        super.put(CODE_TAG, error);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static BaseResponse success()
    {
        return BaseResponse.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static BaseResponse success(Object data)
    {
        return BaseResponse.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static BaseResponse success(String msg)
    {
        return BaseResponse.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static BaseResponse success(String msg, Object data)
    {
        return new BaseResponse(HttpStatus.OK, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static BaseResponse error()
    {
        return BaseResponse.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static BaseResponse error(String msg)
    {
        return BaseResponse.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static BaseResponse error(String msg, Object data)
    {
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static BaseResponse error(int code, String msg)
    {
        return new BaseResponse(code, msg, null);
    }
}
