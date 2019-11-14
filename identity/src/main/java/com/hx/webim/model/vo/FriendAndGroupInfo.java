package com.hx.webim.model.vo;

import com.hx.webim.model.pojo.User;
import com.hx.webim.model.vo.FriendList;
import com.hx.webim.model.vo.GroupList;

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

    @Override
    public String toString() {
        return "FriendAndGroupInfo{" +
                "mine=" + mine +
                ", friend=" + friend +
                ", group=" + group +
                '}';
    }
}
