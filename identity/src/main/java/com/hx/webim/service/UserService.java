package com.hx.webim.service;

import com.hx.webim.model.dto.SystemMessage;
import com.hx.webim.model.pojo.AddMessage;
import com.hx.webim.model.vo.FriendList;
import com.hx.webim.model.vo.GroupList;
import com.hx.webim.model.pojo.User;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserService {
    boolean register(User user);
    boolean active(User user);
    boolean existEmail(String email) ;
    User getUserInfoById(Integer uid);
    List<FriendList> findFriendGroupsById(Integer uid);
    List<GroupList> findGroupsById(Integer uid);
    @Cacheable(cacheNames = {"GroupMembers"})
    List<User> findGroupMembersByGid(Integer gid);
    User login(User user);
    void add_msg(AddMessage addMessage);
    List<SystemMessage> get_msg(Integer uid);
}
