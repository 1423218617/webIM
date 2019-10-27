package com.hx.webim.chatControlller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ChatSocketTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void ss(){
        stringRedisTemplate.opsForValue().set("sss","aaa");
    }

}
