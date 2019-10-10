package com.hx.webim.model.domain;

import com.hx.webim.model.pojo.User;

import java.util.List;

public class FriendAndGroupInfo {
    private User mime;
    private List<FriendList> friend;
    private List<GroupList> group;

    public User getMime() {
        return mime;
    }

    public void setMime(User mime) {
        this.mime = mime;
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
