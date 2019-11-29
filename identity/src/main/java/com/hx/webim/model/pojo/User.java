package com.hx.webim.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class User implements Serializable {


    private Integer id;
    private String username;
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
    private  String birthday;
    private String blood_type;
    private String job;
    private String phoneNumber;
    private String qq;
    private String type;
    private String wechat;

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
                ", birthday='" + birthday + '\'' +
                ", blood_type='" + blood_type + '\'' +
                ", job='" + job + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", qq='" + qq + '\'' +
                ", type='" + type + '\'' +
                ", wechat='" + wechat + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
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
