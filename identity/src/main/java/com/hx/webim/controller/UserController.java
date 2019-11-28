package com.hx.webim.controller;


import com.hx.webim.common.ResultEnum;
import com.hx.webim.common.SystemMsgStatusEnum;
import com.hx.webim.model.TokenModel;
import com.hx.webim.model.dto.SystemMessage;
import com.hx.webim.model.pojo.AddMessage;
import com.hx.webim.model.vo.*;
import com.hx.webim.model.pojo.User;
import com.hx.webim.service.UserService;
import com.hx.webim.chatControlller.ChatSocket;
import com.hx.webim.util.DateUtil;
import com.hx.webim.util.JsonUtils;
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
    public Object add_msg(HttpServletRequest request,@RequestParam Integer to,
                          @RequestParam Integer msgType,@RequestParam String remark,
                          @RequestParam Integer mygroupIdx){
        TokenModel t =RequestUtil.getTokenByRequest(request);
        Integer uid=TokenUtil.getIdByToken(t);
        AddMessage addMessage=new AddMessage();
        addMessage.setFrom_uid(uid);
        addMessage.setTo_uid(to);
        addMessage.setRemark(remark);
        addMessage.setType(msgType);
        addMessage.setAgree(SystemMsgStatusEnum.UNTREATED.getCode());
        addMessage.setGroup_id(mygroupIdx);
        addMessage.setTime(DateUtil.getDate());
        userService.add_msg(addMessage);
        ResultVo resultVo=new ResultVo(ResultEnum.SUCCESS);
        return resultVo ;
    }

    @ResponseBody
    @GetMapping("getMsgBox")
    public Object getMsgBox(HttpServletRequest request){

        List<SystemMessage> systemMessageList=userService.get_msg(126);
        PageResultVo pageResultVo=new PageResultVo();
        pageResultVo.setCode(0);
        pageResultVo.setPages(1);
        pageResultVo.setData(systemMessageList);
        return JsonUtils.objToString(pageResultVo);

        /*
        String a= "{\"code\":0,\"pages\":1,\"data\":[{\"msgIdx\":\"674\",\"msgType\":\"1\",\"from\":\"911088\",\"to\":\"1570845\",\"status\":\"1\",\"remark\":\"\",\"sendTime\":\"1574743527\",\"readTime\":\"1574510341\",\"time\":\"1574743527\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":null,\"username\":\"\u5468\u4e8c\",\"signature\":\"\"},{\"msgIdx\":\"622\",\"msgType\":\"1\",\"from\":\"1570845\",\"to\":\"911117\",\"status\":\"1\",\"remark\":\"ee\",\"sendTime\":\"1574510483\",\"readTime\":\"1574385415\",\"time\":\"1574510483\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":\"2\",\"username\":\"\u56de\u7738\",\"signature\":\"\u89c4\u5212\u6cd5\"},{\"msgIdx\":\"687\",\"msgType\":\"2\",\"from\":\"911085\",\"to\":\"1570845\",\"status\":\"2\",\"remark\":\"\",\"sendTime\":\"1573884999\",\"readTime\":\"1574510352\",\"time\":\"1574510352\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":null,\"username\":\"13275877535\",\"signature\":\"\"},{\"msgIdx\":\"685\",\"msgType\":\"2\",\"from\":\"1570845\",\"to\":\"911100\",\"status\":\"4\",\"remark\":\"\",\"sendTime\":\"1573543104\",\"readTime\":\"1573807928\",\"time\":\"1573807928\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":\"2\",\"username\":\"\u5927\u660e0\",\"signature\":\"fdhfghgbfdhb\"},{\"msgIdx\":\"669\",\"msgType\":\"2\",\"from\":\"1570845\",\"to\":\"911085\",\"status\":\"4\",\"remark\":\"\ucc29\ud55c\uce5c\uad6c\",\"sendTime\":\"1571992313\",\"readTime\":\"1572349077\",\"time\":\"1572349077\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":\"2\",\"username\":\"13275877535\",\"signature\":\"\"},{\"msgIdx\":\"666\",\"msgType\":\"2\",\"from\":\"1570845\",\"to\":\"911067\",\"status\":\"4\",\"remark\":\"\",\"sendTime\":\"1571990701\",\"readTime\":\"1572312173\",\"time\":\"1572312173\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":\"2\",\"username\":\"\u674e\u98dea\",\"signature\":\"\u674e\u98de\u554a\"},{\"msgIdx\":\"667\",\"msgType\":\"2\",\"from\":\"1570845\",\"to\":\"1570855\",\"status\":\"4\",\"remark\":\"\",\"sendTime\":\"1571990754\",\"readTime\":\"1572256901\",\"time\":\"1572256901\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":\"2\",\"username\":\"222222222\",\"signature\":\"\u8428\u6253\u626b\u7684\u963f\u745f\u7fa4\u7fc1\u7fa4\"},{\"msgIdx\":\"670\",\"msgType\":\"2\",\"from\":\"1570845\",\"to\":\"911058\",\"status\":\"4\",\"remark\":\"abc\",\"sendTime\":\"1571992329\",\"readTime\":\"1571992349\",\"time\":\"1571992349\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":\"2\",\"username\":\"\u848b\u848b\",\"signature\":\"\u6211\u4e0d\u662f3\"},{\"msgIdx\":\"592\",\"msgType\":\"3\",\"from\":\"1570845\",\"to\":\"37905088184321\",\"status\":\"1\",\"remark\":\"\",\"sendTime\":\"1563541072\",\"readTime\":null,\"time\":\"1563541072\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":null,\"groupName\":\"111111\",\"groupIdx\":\"37905088184321\",\"username\":\"\ud55c\uae00\ub098\ub77c\",\"signature\":\"111111112\"},{\"msgIdx\":\"79\",\"msgType\":\"1\",\"from\":\"1570845\",\"to\":\"1570845\",\"status\":\"1\",\"remark\":\"\",\"sendTime\":\"1542173072\",\"readTime\":null,\"time\":\"1542173072\",\"adminGroup\":\"0\",\"handle\":null,\"mygroupIdx\":\"324\",\"username\":\"\ud55c\uae00\ub098\ub77c\",\"signature\":\"111111112\"}],\"memberIdx\":\"1570845\"}";
        PageResultVo pageResultVo1= JsonUtils.stringToObj(a,PageResultVo.class);
        return JsonUtils.objToString(pageResultVo1);*/
    }

    @ResponseBody
    @GetMapping("modify_msg")
    public Object modify_msg(HttpServletRequest request,@RequestParam("msgIdx") Integer msgIdx,@RequestParam("msgType") Integer msgType,
                             @RequestParam("status") Integer status, @RequestParam("mygroupIdx") Integer mygroupIdx,
                             @RequestParam("friendIdx") Integer friendIdx){
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
}
