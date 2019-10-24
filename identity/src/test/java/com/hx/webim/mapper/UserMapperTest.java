package com.hx.webim.mapper;

import com.hx.webim.common.UserActiveStatusEnum;
import com.hx.webim.model.pojo.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

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
}
