package com.hx.webim.model.domain;

import com.hx.webim.model.pojo.User;

import java.util.List;

public class FriendAndGroupInfo {
    private User mine;
    private List<FriendList> friend;
    private List<GroupList> group;

    public User getMine() {
        return mine;
    }

    public void setMine(User mine) {
        this.mine = mine;
    }

    public List<FriendList> getFriend() {
        return friend;
    }

    public void setFriend(List<FriendList> friend) {
        this.friend = friend;
    }

    public List<GroupList> getGroup() {
        return group;
    }

    public void setGroup(List<GroupList> group) {
        this.group = group;
    }
}
