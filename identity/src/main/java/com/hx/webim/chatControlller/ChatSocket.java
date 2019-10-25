package com.hx.webim.chatControlller;


import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.hx.webim.model.dto.ChatMessage;
import com.hx.webim.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/webSocket")
public class ChatSocket {
    private static final Logger log= LoggerFactory.getLogger(ChatSocket.class);

    private Session session;

    private static CopyOnWriteArraySet<ChatSocket> chatSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        chatSocketSet.add(this);
        log.info("【websocket消息】 有新的连接，总数:"+ chatSocketSet.size());
    }

    @OnClose
    public void onClose() {
        chatSocketSet.remove(this);
        log.info("【websocket消息】 连接断开，总数:"+ chatSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        ChatMessage chatMessage= JsonUtils.stringToObj(message,ChatMessage.class);
        if (chatMessage.getType().equals("heartbeat")){
            ChatMessage heartbeat=new ChatMessage();
            heartbeat.setType("heartbeat");
            heartbeat.setContent("服务端发给客户端的心跳包");
            sendMessage(heartbeat.toString());
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
}