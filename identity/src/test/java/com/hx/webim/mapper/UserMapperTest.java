package com.hx.webim.mapper;

import com.hx.webim.model.pojo.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Resource
    UserMapper userMapper;
    @Test
    public void insert() {

        User user=new User();
        user.setUsername("cc");
        user.setPassword("ss");
        user.setActive("asdfgh");
        user.setEmail("asdfghjklktre");
        user.setCreate_date(1570177634);
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
}
