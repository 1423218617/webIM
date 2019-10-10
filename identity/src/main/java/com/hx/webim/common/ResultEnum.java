package com.hx.webim.common;

import com.hx.webim.model.vo.ResultVo;

public enum ResultEnum {
    ;


    private int code;

    private String message;

    ResultEnum(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
