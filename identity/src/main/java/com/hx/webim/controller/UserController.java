package com.hx.webim.controller;


import com.hx.webim.model.pojo.User;
import com.hx.webim.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Identity")
public class UserController {



    @Resource
    private UserService userService;


    /**
     *
     *
     * @description 注册
     * @param user
     *
     */
    @GetMapping(value="/identity")
    public void register(@RequestBody User user) {
        if (userService.saveUser(user)){

        }
    }
}
