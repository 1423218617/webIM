package com.hx.webim.service.impl;

import com.hx.webim.chatControlller.ChatSocket;
import com.hx.webim.model.dto.ChatMessage;
import com.hx.webim.model.dto.SocketMessage;
import com.hx.webim.service.ChatService;
import com.hx.webim.util.JsonUtils;
import com.hx.webim.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public boolean toFriend(SocketMessage message) {
        if (message.getType().equals("friend")){
            int toid=message.getTo();
            ChatMessage chatMessage=new ChatMessage();
            chatMessage.setAvatar("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2670702686,3423622119&fm=26&gp=0.jpg");
            chatMessage.setContent(message.getContent());
            chatMessage.setType(message.getType());
            chatMessage.setMine("false");
            chatMessage.setUsername("cc");
            chatMessage.setCid(message.getFrom());
            chatMessage.setFromid(message.getFrom());
            ConcurrentHashMap<Integer,ChatSocket> concurrentHashMap= ChatSocket.getChatSocketMap();
            ChatSocket chatSocket;
            if (((chatSocket=concurrentHashMap.get(message.getTo()))!=null)&&(RedisUtil.hasKey(String.valueOf(message.getTo())))){
                chatSocket.send(JsonUtils.objToString(chatMessage));
            }else {

            }


        }
        return false;
    }
}
