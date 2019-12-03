package com.hx.webim.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Group {
    private Integer id;
    @JsonProperty("groupname")
    private String group_name;
    private String avatar;
    private String sign;
    @JsonIgnore
    private Integer create_id;
    private Date create_time;


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCreate_id() {
        return create_id;
    }

    public void setCreate_id(Integer create_id) {
        this.create_id = create_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", group_name='" + group_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", create_id='" + create_id + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
