package com.hx.webim.model.vo;

public class ResultVo<T> {


    private int code;
    private String message;
    private T Data;



    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", Data=" + Data +
                '}';
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

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
