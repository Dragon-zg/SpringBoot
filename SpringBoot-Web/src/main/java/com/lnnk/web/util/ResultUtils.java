package com.lnnk.web.util;


import com.lnnk.web.enums.ExceptionCode;
import com.lnnk.web.model.ResultModel;

/**
 * 相应结果工具类
 *
 * @author Lnnk
 * @date: 2017-12-16 20:41
 */
@SuppressWarnings("unchecked")
public class ResultUtils {
    public static <T extends Object> ResultModel<T> success(T object) {
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

    public static ResultModel error(ExceptionCode exceptionCode) {
        ResultModel result = new ResultModel();
        result.setCode(exceptionCode.getCode());
        result.setMsg(exceptionCode.getMsg());
        return result;
    }
}