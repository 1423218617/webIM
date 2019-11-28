package com.hx.webim.mapper;


import com.hx.webim.model.pojo.AddMessage;
import com.hx.webim.model.vo.FriendList;
import com.hx.webim.model.vo.GroupList;
import com.hx.webim.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;

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
    void add_msg(AddMessage addMessage);
    List<AddMessage> get_msg(Integer uid);
    void updateAddMessage(Integer agree,Integer type,Integer msgIdx);
    void insertFriendAndFriend(Integer from_uid,Integer group_id,Integer to_uid);
    AddMessage selectAddMessageById(Integer id);
}
