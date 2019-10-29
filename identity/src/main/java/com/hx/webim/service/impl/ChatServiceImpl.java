package com.hx.webim.service.impl;

import com.hx.webim.chatControlller.ChatSocket;
import com.hx.webim.model.dto.ChatMessage;
import com.hx.webim.model.dto.SocketMessage;
import com.hx.webim.model.dto.UserDto;
import com.hx.webim.service.ChatService;
import com.hx.webim.util.DateUtil;
import com.hx.webim.util.JsonUtils;
import com.hx.webim.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public boolean toFriend(SocketMessage message) {
            int toid=message.getTo();
            ChatMessage chatMessage=new ChatMessage();
            chatMessage.setAvatar("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2670702686,3423622119&fm=26&gp=0.jpg");
            chatMessage.setContent(message.getContent());
            chatMessage.setType(message.getType());
            chatMessage.setMine(false);
            chatMessage.setUsername("cc");
            chatMessage.setTimestamp(DateUtil.getDate()*1000);
            chatMessage.setId(message.getFrom());
            chatMessage.setCid(8);
            ConcurrentHashMap<Integer,ChatSocket> concurrentHashMap= ChatSocket.getChatSocketMap();
            ChatSocket chatSocket=concurrentHashMap.get(message.getTo());
            if (((chatSocket)!=null)&&(RedisUtil.hasKey(String.valueOf(message.getTo())))){
                System.err.println("发送消息" );
                chatSocket.send(JsonUtils.objToString(chatMessage));
                return true;
            }else {
                UserDto userDto=JsonUtils.stringToObj(RedisUtil.get(String.valueOf(toid)),UserDto.class);
                List<ChatMessage> chatMessageList=userDto.getChatMessageList();
                chatMessageList.add(chatMessage);
                userDto.setChatMessageList(chatMessageList);
                return true;
            }
    }
}
