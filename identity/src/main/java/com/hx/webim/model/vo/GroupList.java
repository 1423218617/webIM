package com.hx.webim.model.vo;

public class GroupList {

    private Integer id;
    private String groupname;
    private String avatar;
    private Integer createId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

    @Override
    public String toString() {
        return "GroupList{" +
                "id=" + id +
                ", groupname='" + groupname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createId=" + createId +
                '}';
    }
}
