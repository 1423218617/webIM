package com.hx.webim.util;

import com.hx.webim.model.pojo.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenUtilTest {



    User u=new User();

    @Before
    public void before(){

        u.setId(121);
    }
    @Test
    public void create() {

        String s=TokenUtil.create(u);
        System.out.println(s);
    }

    @Test
    public void stringToModel() {
        System.out.println(TokenUtil.stringToModel(TokenUtil.create(u)));
    }
}
