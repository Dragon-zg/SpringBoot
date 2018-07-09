package com.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 统一返回接口格式结果集
 * @Date: 2017-12-16 19:56
 */
@ApiModel("响应结果实体类")
public class ResultModel<T> {

    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("响应信息")
    private String msg;

    @ApiModelProperty("响应结果")
    private T data;

    public ResultModel() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
