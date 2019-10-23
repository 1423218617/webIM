package com.hx.webim.model;

public class TokenModel {
    private Integer uid;
    private String token;


    @Override
    public String
    toString() {
        return "TokenModel{" +
                "uid=" + uid +
                ", token='" + token + '\'' +
                '}';
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
