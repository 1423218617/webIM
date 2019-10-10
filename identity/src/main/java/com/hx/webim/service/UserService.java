package com.hx.webim.service;

import com.hx.webim.Exception.UserException;
import com.hx.webim.model.domain.FriendList;
import com.hx.webim.model.domain.GroupList;
import com.hx.webim.model.pojo.Group;
import com.hx.webim.model.pojo.User;

import java.util.List;

public interface UserService {
    boolean register(User user);
    boolean active(User user);
    boolean existEmail(String email) ;
    User getUserInfoById(Integer uid);
    List<FriendList> findFriendGroupsById(Integer uid);
    List<GroupList> findGroupsById(Integer uid);
}
