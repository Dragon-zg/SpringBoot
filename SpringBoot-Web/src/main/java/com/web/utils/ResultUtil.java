package com.web.utils;


import com.common.enums.ExceptionCode;
import com.web.model.ResultModel;

/**
 * @Description: 相应结果工具类
 * @Date: 2017-12-16 20:41
 */
public class ResultUtil {
    public static ResultModel success(Object object) {
        ResultModel result = new ResultModel();
        result.setCode(ExceptionCode.SUCCESS.getCode());
        result.setMsg(ExceptionCode.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static ResultModel success() {
        return success(null);
    }

    public static ResultModel error(Integer code, String msg) {
        ResultModel result = new ResultModel();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}