package com.hx.webim.service.impl;

import com.hx.webim.model.pojo.User;
import com.hx.webim.service.UserService;
import com.hx.webim.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {


    @Resource
    private UserService userService;
    @Test
    public void saveUser() {
        User user=new User();
        user.setPassword("22");
        user.setUsername("22");
        user.setEmail("1423218617@qq.com");
        user.setCreate_date(DateUtil.getDate());
        userService.register(user);
    }

    @Test
    public void existEmail()  throws Exception {
        System.out.println(userService.existEmail("1423218617@qq.com"));
    }
}