package com.hx.webim.controller;


import com.hx.webim.Exception.UserException;
import com.hx.webim.model.pojo.User;
import com.hx.webim.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/identity")
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
    @GetMapping(value="/register")
    public void register(@RequestBody User user) {
        if (userService.register(user)){

        }
    }



    @GetMapping("/active/{activeCode}")
    public boolean active(@PathVariable String activeCode){
        User user=new User();
        user.setActive(activeCode);
        if(userService.active(user)){
            return true;
        }
        return false;
    }

    @RequestMapping("/existEmail")
    public boolean existEmail(@RequestParam String email)   {
        userService.existEmail(email);

        return  true;

    }
}
