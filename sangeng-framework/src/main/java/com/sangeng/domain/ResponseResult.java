package com.sangeng.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sangeng.enums.AppHttpCodeEnum;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult implements Serializable {
    private static final long serialVersionUID = 2868731317035706582L;
    private final Integer code;
    private final String msg;
    private final Object data;

    private static final ResponseResult SUCCESS_RESULT = new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg());

    public ResponseResult() {
        this(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), null);
    }

    public ResponseResult(Integer code, Object data) {
        this(code, null, data);
    }

    public ResponseResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this(code, msg, null);
    }

    public static ResponseResult errorResult(int code, String msg) {
        return new ResponseResult(code, msg);
    }

    public static ResponseResult okResult() {
        return SUCCESS_RESULT;
    }

    public static ResponseResult okResult(int code, String msg) {
        return new ResponseResult(code, msg);
    }

    public static ResponseResult okResult(Object data) {
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), null, data);
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums){
        return new ResponseResult(enums.getCode(), enums.getMsg());
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums, String msg){
        return new ResponseResult(enums.getCode(), msg);
    }

    public static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums){
        return new ResponseResult(enums.getCode(), enums.getMsg());
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums, String msg){
        return new ResponseResult(enums.getCode(), msg);
    }

    public ResponseResult error(Integer code, String msg) {
        return new ResponseResult(code, msg);
    }

    public ResponseResult ok(Integer code, Object data) {
        return new ResponseResult(code, null, data);
    }

    public ResponseResult ok(Integer code, Object data, String msg) {
        return new ResponseResult(code, msg, data);
    }

    public ResponseResult ok(Object data) {
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), null, data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

}
