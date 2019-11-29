package com.hx.webim.service.impl;

import com.hx.webim.Exception.UserException;
import com.hx.webim.common.ExceptionEnum;
import com.hx.webim.common.UserActiveStatusEnum;
import com.hx.webim.mapper.UserMapper;
import com.hx.webim.model.dto.ChatMessage;
import com.hx.webim.model.dto.SystemMessage;
import com.hx.webim.model.dto.UserDto;
import com.hx.webim.model.pojo.AddMessage;
import com.hx.webim.model.vo.FriendList;
import com.hx.webim.model.vo.GroupList;
import com.hx.webim.model.pojo.User;
import com.hx.webim.model.vo.PageResultVo;
import com.hx.webim.model.vo.UserInfo;
import com.hx.webim.service.UserService;
import com.hx.webim.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;



@Service
public class UserServiceImpl implements UserService {

    private static final Logger log= LoggerFactory.getLogger(UserServiceImpl.class);

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
                        +"<a href=http=//127.0.0.1=8082/identity/active/"+user.getActive()+">点击激活并登陆</a>");
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

        if (!RedisUtil.hasKey(String.valueOf(u.getId()))){
            UserDto userDto=new UserDto();
            BeanUtils.copyProperties(u,userDto);
            userDto.setStatus("online");
            String userDtoString= JsonUtils.objToString(userDto);
            RedisUtil.set(String.valueOf(userDto.getId()),userDtoString);
        }else {
            UserDto userDto= JsonUtils.stringToObj(RedisUtil.get(String.valueOf(u.getId())),UserDto.class);
            BeanUtils.copyProperties(u,userDto);
            RedisUtil.set(String.valueOf(u.getId()),JsonUtils.objToString(userDto));
        }

        return u;
    }

    @Override
    public void add_msg(AddMessage addMessage) {
        userMapper.add_msg(addMessage);
    }

    @Override
    public List<SystemMessage> get_msg(Integer uid) {

        List<SystemMessage> systemMessageList=new ArrayList<>();
        List<AddMessage> addMessageList=userMapper.get_msg(uid);
        addMessageList.forEach(addMessage -> {
            String username;
            Integer username_id;
            if (addMessage.getFrom_uid().equals(uid)){
                username_id=addMessage.getTo_uid();
            }else {
                username_id=addMessage.getFrom_uid();
            }
            UserDto userDto=JsonUtils.stringToObj(RedisUtil.get(String.valueOf(username_id)),UserDto.class);
            username=userDto.getUsername();
            SystemMessage systemMessage=new SystemMessage();
            systemMessage.setFrom(addMessage.getFrom_uid());
            systemMessage.setTo(addMessage.getTo_uid());
            systemMessage.setMsgType(addMessage.getType());
            systemMessage.setTime(addMessage.getTime());
            systemMessage.setStatus(addMessage.getAgree());
            systemMessage.setMsgIdx(addMessage.getId());
            systemMessage.setUsername(username);
            systemMessage.setSendTime(DateUtil.getDate()*1000);
            systemMessage.setReadTime(DateUtil.getDate()*1000);
            systemMessage.setRemark(addMessage.getRemark());
            systemMessageList.add(systemMessage);

        });


        return systemMessageList;


    }

    @Transactional
    @Override
    public boolean modify_msg(Integer msgIdx, Integer msgType, Integer status, Integer mygroupIdx, Integer friendIdx,Integer uid) {
      //  /modify_msg?msgIdx=41&msgType=2&status=2&mygroupIdx=4&friendIdx=126
        userMapper.updateAddMessage(status,msgType,msgIdx);
        if (status.equals(3)||status.equals(1)){
            return true;
        }
        userMapper.insertFriendAndFriend(uid,mygroupIdx,friendIdx);
        AddMessage addMessage=userMapper.selectAddMessageById(msgIdx);
        userMapper.insertFriendAndFriend(addMessage.getFrom_uid(),addMessage.getGroup_id(),addMessage.getTo_uid());
        return true;
    }

    @Override
    public UserInfo getInformation(Integer id, String type) {
        UserDto userDto=JsonUtils.stringToObj(RedisUtil.get(String.valueOf(id)),UserDto.class);
        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(userDto,userInfo);
        userInfo.setEmailAddress(userDto.getEmail());
        userInfo.setMemberSex(userDto.getSex());
        userInfo.setSignature(userDto.getSign());
        userInfo.setType(type);
        userInfo.setMemberName(userDto.getUsername());
        userInfo.setMemberIdx(userDto.getId());
        return userInfo;
    }
}
