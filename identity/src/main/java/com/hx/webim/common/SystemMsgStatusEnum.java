package com.hx.webim.common;

public enum SystemMsgStatusEnum {
    AGREE(1,"同意"),REFUSE(2,"拒绝"),UNTREATED(0,"未处理")

    ;
    private Integer code;
    private String msg;

    SystemMsgStatusEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
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
}
