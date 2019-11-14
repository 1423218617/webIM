package com.hx.webim.controller;


import com.hx.webim.model.TokenModel;
import com.hx.webim.model.vo.FriendAndGroupInfo;
import com.hx.webim.model.vo.FriendList;
import com.hx.webim.model.vo.GroupList;
import com.hx.webim.model.vo.GroupMemberInfo;
import com.hx.webim.model.pojo.User;
import com.hx.webim.model.vo.ResultVo;
import com.hx.webim.service.UserService;
import com.hx.webim.chatControlller.ChatSocket;
import com.hx.webim.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/identity")
public class UserController {

    private final static Logger log= LoggerFactory.getLogger(UserController.class);



    @Resource
    private ChatSocket chatSocket;

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
             response.sendRedirect("/login.html#tologin?status=1");
        }
        return "redirect:/login.html#tologin?status=1";
    }

    @PostMapping("/existEmail")
    @ResponseBody
    public Object existEmail(@RequestParam String email)   {
        userService.existEmail(email);

        return false;

    }

    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User user){
        ResultVo resultVo=new ResultVo();
        if(!(StringUtils.isBlank(user.getEmail())&&StringUtils.isBlank(user.getPassword()))){
            User u=userService.login(user);
            resultVo.setCode(0);
            resultVo.setMsg("登陆成功");
            TokenModel tokenModel= new TokenModel();
            String t= TokenUtil.create(u);
            tokenModel.setUid(u.getId());
            tokenModel.setToken(t);
            resultVo.setData(tokenModel);
            return resultVo;
        }
        resultVo.setCode(1);
        resultVo.setMsg("账号或密码不能为空");
        return resultVo;
    }

    @GetMapping("init/{userId}")
    @ResponseBody
    public Object index(@PathVariable String userId,HttpServletRequest request){
        Integer id=Integer.parseInt(userId);
        User user= userService.getUserInfoById(id);
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
        log.info(resultVo.toString());
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

    

}
