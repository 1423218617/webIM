package com.hx.webim.controller;


import com.hx.webim.Exception.UserException;
import com.hx.webim.model.domain.FriendAndGroupInfo;
import com.hx.webim.model.domain.FriendList;
import com.hx.webim.model.domain.GroupList;
import com.hx.webim.model.pojo.User;
import com.hx.webim.model.vo.ResultVo;
import com.hx.webim.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
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
    @PostMapping(value="/register")
    @ResponseBody
    public Object register(@RequestBody User user) {
        if (userService.register(user)){
            ResultVo resultVo=new ResultVo();
            resultVo.setMsg("注册成功");
            return resultVo;
        }
        return false;
    }



    @GetMapping("/active/{activeCode}")
    @ResponseBody
    public Object active(@PathVariable String activeCode, HttpServletResponse response) throws Exception{
        User user=new User();
        user.setActive(activeCode);
        if(userService.active(user)){
             response.sendRedirect("/#tologin?status=1");
        }
        return "redirect:/#tologin?status=1";
    }

    @PostMapping("/existEmail")
    @ResponseBody
    public Object existEmail(@RequestParam String email)   {
        userService.existEmail(email);

        return false;

    }

    @GetMapping("init/{userId}")
    @ResponseBody
    public Object index(@PathVariable String userId){
        Integer id=Integer.parseInt(userId);
        User user= userService.getUserInfoById(id);
        System.out.println(user);
        List <FriendList> friend=userService.findFriendGroupsById(id);
        FriendAndGroupInfo friendAndGroupInfo=new FriendAndGroupInfo();
        friendAndGroupInfo.setMine(user);
        friendAndGroupInfo.setFriend(friend);
        List<GroupList> group=userService.findGroupsById(id);
        friendAndGroupInfo.setGroup(group);
        ResultVo<FriendAndGroupInfo> resultVo=new ResultVo();
        resultVo.setData(friendAndGroupInfo);
        resultVo.setCode(1);
        resultVo.setMsg("ok");
        return resultVo;
    }
}
