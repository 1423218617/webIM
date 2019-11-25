package com.hx.webim.model.pojo;

public class AddMessage {
    private Integer id;
    private Integer from_uid;
    private Integer to_uid;
    private Integer group_id;
    private String remark;
    private Integer agree;
    private Integer type;
    private Long time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(Integer from_uid) {
        this.from_uid = from_uid;
    }

    public Integer getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(Integer to_uid) {
        this.to_uid = to_uid;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAgree() {
        return agree;
    }

    public void setAgree(Integer agree) {
        this.agree = agree;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
