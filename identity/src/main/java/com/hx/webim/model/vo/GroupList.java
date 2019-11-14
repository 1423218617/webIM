package com.hx.webim.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupList {

    private Integer id;
    @JsonProperty("groupname")
    private String group_name;
    private String avatar;
    private Integer createId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @Override
    public String toString() {
        return "GroupList{" +
                "id=" + id +
                ", group_name='" + group_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createId=" + createId +
                '}';
    }
}
