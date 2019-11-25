package com.hx.webim.controller;


import com.hx.webim.common.ResultEnum;
import com.hx.webim.model.TokenModel;
import com.hx.webim.model.dto.SystemMessage;
import com.hx.webim.model.pojo.AddMessage;
import com.hx.webim.model.vo.FriendAndGroupInfo;
import com.hx.webim.model.vo.FriendList;
import com.hx.webim.model.vo.GroupList;
import com.hx.webim.model.vo.GroupMemberInfo;
import com.hx.webim.model.pojo.User;
import com.hx.webim.model.vo.ResultVo;
import com.hx.webim.service.UserService;
import com.hx.webim.chatControlller.ChatSocket;
import com.hx.webim.util.DateUtil;
import com.hx.webim.util.RequestUtil;
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

    @GetMapping("findFriendTotal")
    @ResponseBody
    public Object findFriendTotal(@RequestParam("type") String type,@RequestParam("value") String value,@RequestParam(value = "page",required = false) String page) {

        if(page!=null)
        return "{'code':'0','msg':'','data':[{'memberIdx':'911058','memberName':'\u848b\u848b','signature':'\u6211\u4e0d\u662f3','birthday':'2017\u5e7412\u670814\u65e5','memberSex':'1'}]}";
        return "{'code':0,'count':'','data':{'count':'1','limit':'1'}}";

    }

    @GetMapping("getRecommend")
    @ResponseBody
    public Object getRecommend(){
        return "{'code':'0','msg':'','data':[{'memberIdx':'911058','memberName':'\u848b\u848b','signature':'\u6211\u4e0d\u662f3','birthday':'2017\u5e7412\u670814\u65e5','memberSex':'1'}]}";
    }

    @GetMapping("add_msg")
    @ResponseBody
    public Object add_msg(HttpServletRequest request,@RequestParam Integer to,@RequestParam Integer msgType,@RequestParam String remark,@RequestParam Integer group_id){
        TokenModel t =RequestUtil.getTokenByRequest(request);
        Integer uid=TokenUtil.getIdByToken(t);
        AddMessage addMessage=new AddMessage();
        addMessage.setFrom_uid(uid);
        addMessage.setTo_uid(to);
        addMessage.setRemark(remark);
        addMessage.setType(msgType);
        addMessage.setAgree(0);
        addMessage.setGroup_id(group_id);
        addMessage.setTime(DateUtil.getDate());
        userService.add_msg(addMessage);
        ResultVo resultVo=new ResultVo(ResultEnum.SUCCESS);
        return resultVo ;
    }

    @ResponseBody
    @GetMapping("getMsgBox")
    public Object getMsgBox(HttpServletRequest request){

        List<SystemMessage> systemMessageList=userService.get_msg(126);
        ResultVo<List> resultVo=new ResultVo<>(ResultEnum.SUCCESS);
        resultVo.setData(systemMessageList);
        return resultVo;
    }
}
