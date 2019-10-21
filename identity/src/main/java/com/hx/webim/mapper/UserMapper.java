package com.hx.webim.mapper;


import com.hx.webim.model.domain.FriendList;
import com.hx.webim.model.domain.GroupList;
import com.hx.webim.model.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertUser(User user);
    User selectUser(User user);
    void updateUser(User user);
    List<String> UserAllEmail();
    List<FriendList> findFriendGroupsById(Integer uid);
    List<User> findUsersByFriendGroupIds(Integer fgid);
    List<GroupList> findGroupById(Integer uid);
    List<User> findGroupMembersByGid(Integer gid);

}
