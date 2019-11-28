package com.hx.webim.mapper;

import com.hx.webim.common.UserActiveStatusEnum;
import com.hx.webim.model.pojo.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserMapperTest {

    @Resource
    UserMapper userMapper;
    @Test
    public void insert() {

        User user=new User();
        user.setUsername("cc");
        user.setPassword("hx");
        user.setActive("srtyuio");
        user.setEmail("1432@qq.com");
        user.setCreate_date(1570177634);
        user.setIs_active(UserActiveStatusEnum.YES.getCode());
        userMapper.insertUser(user);
    }

    @Test
    public void findUsersByFriendGroupIds() {
        System.out.println(userMapper.findUsersByFriendGroupIds(1));
    }

    @Test
    public void findGroupById() {
        System.out.println(userMapper.findGroupById(121));
    }

    @Test
    public void updateAddMessage() {
        userMapper.updateAddMessage(1,1,13);
    }

    @Test
    public void insertFriendAndFriend() {
        userMapper.insertFriendAndFriend(333,444,555);
    }

    @Test
    public void selectAddMessageById() {
        System.out.println(userMapper.selectAddMessageById(13));
    }
}
