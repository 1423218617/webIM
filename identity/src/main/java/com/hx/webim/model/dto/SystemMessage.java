package com.hx.webim.model.dto;

public class SystemMessage {


    private String msgIdx;
    private String msgType;
    private String from;
    private String to;
    private String status;
    private String remark;
    private String sendTime;
    private String readTime;
    private String time;
    private String adminGroup;
    private String handle;
    private String mygroupIdx;
    private String username;
    private String signature;


    public String getMsgIdx() {
        return msgIdx;
    }

    public void setMsgIdx(String msgIdx) {
        this.msgIdx = msgIdx;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAdminGroup() {
        return adminGroup;
    }

    public void setAdminGroup(String adminGroup) {
        this.adminGroup = adminGroup;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getMygroupIdx() {
        return mygroupIdx;
    }

    public void setMygroupIdx(String mygroupIdx) {
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
