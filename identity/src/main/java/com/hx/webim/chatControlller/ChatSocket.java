package com.hx.webim.chatControlller;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.hx.webim.model.dto.ChatMessage;
import com.hx.webim.util.JsonUtils;
import com.hx.webim.util.RedisUtil;
import com.hx.webim.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/webSocket")
public class ChatSocket {


    private static final Logger log= LoggerFactory.getLogger(ChatSocket.class);

    private String uid;

    private Session session;

    private static CopyOnWriteArraySet<ChatSocket> chatSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        uid= UUIDUtil.getUUID();
        this.session = session;
        chatSocketSet.add(this);
        log.info("【websocket消息】 有新的连接，总数:"+ chatSocketSet.size());
    }

    @OnClose
    public void onClose() {
        chatSocketSet.remove(this);
        RedisUtil.delete(uid);
        log.info("【websocket消息】 连接断开，总数:"+ chatSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        ChatMessage chatMessage= JsonUtils.stringToObj(message,ChatMessage.class);
        switch (chatMessage.getType()){
            case "beat":
                System.out.println("set"+uid);
                RedisUtil.set(uid,chatMessage.getContent(),60);
                break;
        }
        if (chatMessage.getType().equals("heartbeat")){
            ChatMessage heartbeat=new ChatMessage();
            heartbeat.setType("heartbeat");
            heartbeat.setContent("服务端发给客户端的心跳包");
            try {
                session.getBasicRemote().sendText(heartbeat.toString());
            } catch (IOException e) {
                e.printStackTrace();
                log.error("发送消息失败");
            }
            return;
        }
        log.info("【websocket消息】收到客户端发来的消息"+message);
    }

    public void sendMessage(String message) {
        for(ChatSocket chatSocket : chatSocketSet) {
            System.err.println("【websocket消息】广播消息,message="+message);
            try {
                chatSocket.session.getBasicRemote().sendText(message);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(fixedRate=5000)
    private void configureTasks() {
        if (RedisUtil.hasKey(this.uid)){
            log.info("用户在线");
        } else {
            System.out.println("get"+this.uid);
            System.out.println(RedisUtil.get(this.uid));
            log.info("用户不在线");
        }
    }


}