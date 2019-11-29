package com.hx.webim.common;

public enum SystemMsgStatusEnum {
    AGREE(2,2,"同意"),
    REFUSE(2,3,"拒绝"),
    UNTREATED(1,0,"未处理")

    ;
    private Integer type;
    private Integer status;
    private String msg;

    SystemMsgStatusEnum(Integer type, Integer status, String msg) {
        this.type = type;
        this.status = status;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
