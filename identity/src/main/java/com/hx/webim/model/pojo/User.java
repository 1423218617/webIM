package com.hx.webim.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {


    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String sign;
    private String email;
    private String avatar;
    private Integer sex;
    @JsonIgnore
    private Integer is_active;
    @JsonIgnore
    private String active;
    private String status;
    private long create_date;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sign='" + sign + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", is_active=" + is_active +
                ", active='" + active + '\'' +
                ", status='" + status + '\'' +
                ", create_date=" + create_date +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }
}
