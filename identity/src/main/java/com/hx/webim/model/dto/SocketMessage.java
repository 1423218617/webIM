package com.hx.webim.model.dto;

public class SocketMessage {

    private Integer from;
    private Integer to;
    private String type;
    private String content;
    private String token;

    @Override
    public String toString() {
        return "SocketMessage{" +
                "from=" + from +
                ", to=" + to +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
