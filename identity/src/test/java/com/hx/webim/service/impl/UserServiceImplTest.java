package com.hx.webim.service.impl;

import com.hx.webim.model.pojo.User;
import com.hx.webim.service.UserService;
import com.hx.webim.util.DateUtil;
import com.hx.webim.util.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Test
    public void login() {
        User user=new User();
        user.setPassword("hx");
        user.setEmail("1423218617@qq.com");
        //userService.login(user);
        System.out.println(SecurityUtil.matches(user.getPassword(),"$2a$10$vyBDhShMoxdg3hFQIa36ru3bfJveUDK5m9uHU6xUTV0kn95DUVpwC"));
        System.out.println(SecurityUtil.encrypt("hx"));
    }
}