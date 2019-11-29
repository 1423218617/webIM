package com.hx.webim.service.impl;

import com.hx.webim.chatControlller.ChatSocket;
import com.hx.webim.model.dto.ChatMessage;
import com.hx.webim.model.dto.SocketMessage;
import com.hx.webim.model.dto.UserDto;
import com.hx.webim.model.pojo.User;
import com.hx.webim.service.ChatService;
import com.hx.webim.service.UserService;
import com.hx.webim.util.DateUtil;
import com.hx.webim.util.JsonUtils;
import com.hx.webim.util.RedisUtil;
import com.hx.webim.util.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private UserService userService;

    @Override
    public boolean toFriend(SocketMessage message) {
            Integer fromid=message.getFrom();
            UserDto from_userDto=JsonUtils.stringToObj(RedisUtil.get(String.valueOf(fromid)),UserDto.class);
            int toid=message.getTo();
            ChatMessage chatMessage=new ChatMessage();
            chatMessage.setAvatar("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2670702686,3423622119&fm=26&gp=0.jpg");
            chatMessage.setContent(message.getContent());
            chatMessage.setType(message.getType());
            chatMessage.setMine(false);
            chatMessage.setUsername(from_userDto.getUsername());
            chatMessage.setTimestamp(DateUtil.getDate()*1000);
            chatMessage.setId(message.getFrom());
            chatMessage.setCid((int)(Math.random()*100));
            ConcurrentHashMap<Integer,ChatSocket> concurrentHashMap= ChatSocket.getChatSocketMap();
            ChatSocket chatSocket=concurrentHashMap.get(message.getTo());
            if (((chatSocket)!=null)&&(RedisUtil.hasKey(String.valueOf(message.getTo())))){
                System.err.println("发送消息" );
                chatSocket.send(JsonUtils.objToString(chatMessage));
                return true;
            }else {
                UserDto to_userDto=JsonUtils.stringToObj(RedisUtil.get(String.valueOf(toid)),UserDto.class);
                List<ChatMessage> chatMessageList=to_userDto.getChatMessageList();
                chatMessageList.add(chatMessage);
                to_userDto.setChatMessageList(chatMessageList);
                RedisUtil.set(String.valueOf(toid),JsonUtils.objToString(to_userDto));
                return true;
            }
    }

    @Override
    public boolean toGroup(SocketMessage message) {
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setFromid(message.getFrom());
        chatMessage.setContent(message.getContent());
        chatMessage.setType(message.getType());
        chatMessage.setTimestamp(DateUtil.getDate()*1000);
        chatMessage.setUsername("cc");
        chatMessage.setId(message.getTo());
        chatMessage.setAvatar("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2670702686,3423622119&fm=26&gp=0.jpg");
        chatMessage.setMine(false);
        List<User> groupMemberList= userService.findGroupMembersByGid(message.getTo());
        ConcurrentHashMap<Integer,ChatSocket> concurrentHashMap= ChatSocket.getChatSocketMap();
        groupMemberList.forEach(user -> {
            if (concurrentHashMap.containsKey(user.getId())&&RedisUtil.hasKey(String.valueOf(message.getTo()))){
                if (!user.getId().equals(message.getFrom())){
                    ChatSocket chatSocket= concurrentHashMap.get(user.getId());
                    chatSocket.send(JsonUtils.objToString(chatMessage));
                }
            }else {
                UserDto userDto=JsonUtils.stringToObj(RedisUtil.get(String.valueOf(user.getId())),UserDto.class);
                List<ChatMessage> chatMessageList=userDto.getChatMessageList();
                chatMessageList.add(chatMessage);
                userDto.setChatMessageList(chatMessageList);
                RedisUtil.set(String.valueOf(user.getId()),JsonUtils.objToString(userDto));
            }
        });
        return true;
    }
}
