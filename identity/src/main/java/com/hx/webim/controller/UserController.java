package com.hx.webim.controller;


import com.hx.webim.common.ResultEnum;
import com.hx.webim.common.SystemMsgStatusEnum;
import com.hx.webim.mapper.UserMapper;
import com.hx.webim.model.TokenModel;
import com.hx.webim.model.dto.SystemMessage;
import com.hx.webim.model.dto.UserDto;
import com.hx.webim.model.pojo.AddMessage;
import com.hx.webim.model.pojo.Group;
import com.hx.webim.model.vo.*;
import com.hx.webim.model.pojo.User;
import com.hx.webim.service.UserService;
import com.hx.webim.chatControlller.ChatSocket;
import com.hx.webim.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

    @Resource
    private UserMapper userMapper;


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
        ResultVo resultVo=new ResultVo(ResultEnum.SUCCESS);
        switch (type){
            case "friend":
                User user=new User();
                user.setEmail(value);
                user.setUsername(value);
                user.setPhoneNumber(value);
                if(page!=null){
                    List<UserInfo> userInfoList=userService.findFriendTotal(user,type);
                    resultVo.setData(userInfoList);
                    return JsonUtils.objToString(resultVo);
                }
                break;
            case "group":
                Group group=new Group();
                group.setGroup_name(value);
                if (page!=null){
                    List<GroupInfo> groupInfoList=userService.findFriendTotal(group,type);
                    resultVo.setData(groupInfoList);
                    return JsonUtils.objToString(resultVo);
                }
        }
        return "{'code':0,'count':'','data':{'count':'1','limit':'1'}}";

    }


    /**
     * @description 推荐好友
     * @return
     */
    @GetMapping("getRecommend")
    @ResponseBody
    public Object getRecommend(){
        return "{'code':'0','msg':'','data':[{'memberIdx':'911058','memberName':'\u848b\u848b','signature':'\u6211\u4e0d\u662f3','birthday':'2017\u5e7412\u670814\u65e5','memberSex':'1'}]}";
    }

    @GetMapping("add_msg")
    @ResponseBody
    public Object add_msg(HttpServletRequest request,@RequestParam Integer to,
                          @RequestParam Integer msgType,@RequestParam String remark,
                          @RequestParam Integer mygroupIdx){
        if (msgType.equals(3)){
            to=userMapper.selectFounderByGroupId(to).getCreate_id();
        }
        TokenModel t =RequestUtil.getTokenByRequest(request);
        Integer uid=TokenUtil.getIdByToken(t);
        AddMessage addMessage=new AddMessage();
        addMessage.setFrom_uid(uid);
        addMessage.setTo_uid(to);
        addMessage.setRemark(remark);
        addMessage.setType(msgType);
        addMessage.setAgree(SystemMsgStatusEnum.UNTREATED.getStatus());
        addMessage.setGroup_id(mygroupIdx);
        addMessage.setTime(DateUtil.getDate());
        userService.add_msg(addMessage);
        ResultVo resultVo=new ResultVo(ResultEnum.SUCCESS);
        resultVo.setData("ok");
        return resultVo ;
    }

    @ResponseBody
    @GetMapping("getMsgBox")
    public Object getMsgBox(HttpServletRequest request){

        TokenModel t =RequestUtil.getTokenByRequest(request);
        Integer uid=TokenUtil.getIdByToken(t);

        List<SystemMessage> systemMessageList=userService.get_msg(uid);
        PageResultVo pageResultVo=new PageResultVo();
        pageResultVo.setCode(0);
        pageResultVo.setPages(1);
        pageResultVo.setData(systemMessageList);
        pageResultVo.setMemberIdx(uid);
        return JsonUtils.objToString(pageResultVo);


       /* String a="{\"code\":0,\"pages\":1,\"data\":[{\"msgIdx\":\"622\",\"msgType\":\"1\",\"from\":\"1570845\",\"to\":\"911117\",\"status\":\"1\",\"remark\":\"ee\",\"sendTime\":\"1574510483\",\"readTime\":\"1574385415\",\"time\":\"1574510483\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":\"2\",\"username\":\"回眸\",\"signature\":\"规划法\"}],\"memberIdx\":\"1570845\"}  ";
        PageResultVo pageResultVo1=JsonUtils.stringToObj(a,PageResultVo.class);
        System.out.println(pageResultVo+"\n"+pageResultVo1);*/

//        System.out.println(JsonUtils.objToString(pageResultVo)+"\n"+JsonUtils.objToString(pageResultVo1));


//        return JsonUtils.objToString(pageResultVo1);
    }

    @ResponseBody
    @GetMapping("modify_msg")
    public Object modify_msg(HttpServletRequest request,@RequestParam("msgIdx") Integer msgIdx,@RequestParam("msgType") Integer msgType,
                             @RequestParam("status") Integer status, @RequestParam(value = "mygroupIdx",required = false) Integer mygroupIdx,
                             @RequestParam(value = "friendIdx",required = false) Integer friendIdx){
        TokenModel t =RequestUtil.getTokenByRequest(request);
        Integer uid=TokenUtil.getIdByToken(t);
        userService.modify_msg(msgIdx,msgType,status,mygroupIdx,friendIdx,uid);
        return new ResultVo<>(ResultEnum.SUCCESS);

    }

    @ResponseBody
    @GetMapping("set_allread")
    public Object set_allread(){
        return new ResultVo<>(ResultEnum.SUCCESS);
    }

    @ResponseBody
    @GetMapping("userStatus")
    public Object userStatus(@RequestParam("id") String id){
        ResultVo resultVo= new ResultVo(ResultEnum.SUCCESS);
        resultVo.setData("offline");
        return JsonUtils.objToString(resultVo);
    }

    @ResponseBody
    @GetMapping("getInformation")
    public  Object getInformation(@RequestParam(value = "id",required = false) Integer id,@RequestParam("type") String type){
       UserInfo userInfo=userService.getInformation(id,type);
        ResultVo resultVo=new ResultVo(ResultEnum.SUCCESS);
        resultVo.setData(userInfo);
        return resultVo;
    }


    @ResponseBody
    @PostMapping("modify_Information")
    public Object getInformation( HttpServletRequest request, UserInfo userInfo){
        TokenModel t =RequestUtil.getTokenByRequest(request);
        Integer uid=TokenUtil.getIdByToken(t);
        userInfo.setMemberIdx(uid);
        userService.modify_Information(userInfo);
        return new ResultVo<>();
    }

}
