package com.hx.webim.model.dto;

public class SystemMessage {


    private Integer msgIdx;
    private Integer msgType;
    private Integer from;
    private Integer to;
    private Integer status;
    private String remark;
    private long sendTime;
    private long readTime;
    private long time;
    private Integer adminGroup;
    private String handle;
    private Integer mygroupIdx;
    private String username;
    private String signature;

    public Integer getMsgIdx() {
        return msgIdx;
    }

    public void setMsgIdx(Integer msgIdx) {
        this.msgIdx = msgIdx;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getReadTime() {
        return readTime;
    }

    public void setReadTime(long readTime) {
        this.readTime = readTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Integer getAdminGroup() {
        return adminGroup;
    }

    public void setAdminGroup(Integer adminGroup) {
        this.adminGroup = adminGroup;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Integer getMygroupIdx() {
        return mygroupIdx;
    }

    public void setMygroupIdx(Integer mygroupIdx) {
        this.mygroupIdx = mygroupIdx;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
