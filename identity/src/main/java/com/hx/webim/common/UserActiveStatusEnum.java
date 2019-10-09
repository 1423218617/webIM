package com.hx.webim.common;

/**
 *
 * @description 用户是否激活
 */
public enum UserActiveStatusEnum {
    YES(1,"已激活"),
    NO(0,"未激活");
    private int code;
    private String comment;
    UserActiveStatusEnum(int code,String comment){
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
