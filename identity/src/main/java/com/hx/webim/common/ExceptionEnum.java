package com.hx.webim.common;

public enum  ExceptionEnum {
    MAILBOXEXISTS("邮箱重复注册");
    private String message;
    ExceptionEnum(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
