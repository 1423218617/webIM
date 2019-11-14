package com.hx.webim.model.vo;

import com.hx.webim.model.pojo.User;

import java.util.List;

public class GroupMemberInfo {
    List<User> MemberList;

    public List<User> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<User> memberList) {
        MemberList = memberList;
    }

    @Override
    public String toString() {
        return "GroupMemberInfo{" +
                "MemberList=" + MemberList +
                '}';
    }
}
