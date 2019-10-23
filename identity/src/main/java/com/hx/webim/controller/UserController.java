package com.hx.webim.controller;


import com.hx.webim.Exception.UserException;
import com.hx.webim.model.domain.FriendAndGroupInfo;
import com.hx.webim.model.domain.FriendList;
import com.hx.webim.model.domain.GroupList;
import com.hx.webim.model.domain.GroupMemberInfo;
import com.hx.webim.model.pojo.User;
import com.hx.webim.model.vo.ResultVo;
import com.hx.webim.service.UserService;
import com.hx.webim.socketmessage.WebSocket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.Multipart;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/identity")
public class UserController {



    @Resource
    private UserService userService;

    @Resource
    private WebSocket webSocket;

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

    @PostMapping("/login")
    @ResponseBody
    public Object login(String email,String password){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(0);
        return resultVo;
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
        resultVo.setCode(0);
        resultVo.setMsg("ok");
        return resultVo;
    }

    @GetMapping("groupMembers/{gid}")
    @ResponseBody
    public Object groupMembers(@PathVariable Integer gid){
        List<User> memberList=userService.findGroupMembersByGid(gid);
        GroupMemberInfo groupMemberInfo=new GroupMemberInfo();
        groupMemberInfo.setMemberList(memberList);
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(200);
        resultVo.setMsg("群成员信息");
        resultVo.setData(groupMemberInfo);
        return resultVo;
    }

    @PostMapping("uploadImage")
    @ResponseBody
    public Object uploadImage(@RequestParam("file") MultipartFile file)throws IOException {
        FileOutputStream fileOutputStream=new FileOutputStream(new File("uploadfile/"+file.getOriginalFilename()));
        fileOutputStream.write(file.getBytes());
        System.out.println(file.getSize());
        return new ResultVo<>();
    }

    @GetMapping("socket")
    @ResponseBody
    public Object ss(){
        webSocket.sendMessage("ssssss");
        return "sss";
    }
}
