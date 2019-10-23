package com.hx.webim.service.impl;

import com.hx.webim.Exception.UserException;
import com.hx.webim.common.ExceptionEnum;
import com.hx.webim.common.UserActiveStatusEnum;
import com.hx.webim.mapper.UserMapper;
import com.hx.webim.model.domain.FriendList;
import com.hx.webim.model.domain.GroupList;
import com.hx.webim.model.pojo.Group;
import com.hx.webim.model.pojo.User;
import com.hx.webim.service.UserService;
import com.hx.webim.util.DateUtil;
import com.hx.webim.util.MailUtil;
import com.hx.webim.util.SecurityUtil;
import com.hx.webim.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public boolean register(User user) {

        if (user==null|| StringUtils.isBlank(user.getUsername())
                ||StringUtils.isBlank(user.getPassword())
                ||StringUtils.isBlank(user.getEmail())){
            return false;
        }
        user.setCreate_date(DateUtil.getDate());
        user.setPassword(SecurityUtil.encrypt(user.getPassword()));
        user.setIs_active(UserActiveStatusEnum.NO.getCode());
        user.setActive(UUIDUtil.getUUID());
        userMapper.insertUser(user);
        MailUtil.sendHtmlMail(user.getEmail(),
                user.getUsername()+",请确定这是你本人注册的账号   "
                        +"<a href=http://47.93.51.212:8080/identity/active/"+user.getActive()+">点击激活并登陆</a>");
        return true;
    }

    @Override
    public boolean active(User user) {
        System.out.println(user);
        User u= userMapper.selectUser(user);
        if (u.getActive().equals(user.getActive())){
            u.setIs_active(UserActiveStatusEnum.YES.getCode());
            userMapper.updateUser(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean existEmail(String email)  {
        List<String> emailList=userMapper.UserAllEmail();
        emailList.forEach(e->{
            if (e.equals(email)){
                throw new UserException(ExceptionEnum.MAILBOXEXISTS.getMessage());
            }
        });
        return false;

    }

    @Override
    public User getUserInfoById(Integer id) {
        User user=new User();
        user.setId(id);
        User u=userMapper.selectUser(user);
        return u;
    }

    /**
     *
     * @descrption 分组
     * @param uid
     * @return
     */
    @Override
    public List<FriendList> findFriendGroupsById(Integer uid) {
        List<FriendList> friendList= userMapper.findFriendGroupsById(uid);
        friendList.forEach(friendList1 -> {
            friendList1.setUserList(userMapper.findUsersByFriendGroupIds(friendList1.getId()));
        });
        return friendList ;
    }

    @Override
    public List<GroupList> findGroupsById(Integer uid) {
        return userMapper.findGroupById(uid) ;
    }

    @Override
    public List<User> findGroupMembersByGid(Integer gid) {
        return userMapper.findGroupMembersByGid(gid);
    }

    @Override
    public User login(User user) {
            User u=userMapper.selectUser(user);
            return u;
    }
}
