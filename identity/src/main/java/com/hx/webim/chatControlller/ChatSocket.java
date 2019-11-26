package com.hx.webim.chatControlller;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.hx.webim.model.TokenModel;
import com.hx.webim.model.dto.ChatMessage;
import com.hx.webim.model.dto.SocketMessage;
import com.hx.webim.model.dto.UserDto;
import com.hx.webim.model.pojo.User;
import com.hx.webim.service.ChatService;
import com.hx.webim.service.UserService;
import com.hx.webim.util.JsonUtils;
import com.hx.webim.util.RedisUtil;
import com.hx.webim.util.TokenUtil;
import com.hx.webim.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@Component
@ServerEndpoint(value = "/webSocket")
public class ChatSocket {


    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ChatSocket.applicationContext = applicationContext;
    }
    private static ChatService chatService;

    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        ChatSocket.userService = userService;
    }

    @Autowired
    public  void setChatService(ChatService chatService) {
        ChatSocket.chatService = chatService;
    }

    private static final Logger log= LoggerFactory.getLogger(ChatSocket.class);

    private Integer uid;

    public Integer getUid() {
        return uid;
    }

    private Session session;

    private static ConcurrentHashMap<Integer,ChatSocket> chatSocketMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Integer, ChatSocket> getChatSocketMap() {
        return chatSocketMap;
    }

    @OnOpen
    public void onOpen(Session session) {

        this.session = session;

    }

    @OnClose
    public void onClose() {
        chatSocketMap.remove(uid);
        log.info("【websocket消息】 连接断开，总数:"+ chatSocketMap.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
//        chatService=applicationContext.getBean(ChatService.class);
        SocketMessage socketMessage = JsonUtils.stringToObj(message, SocketMessage.class);

        log.info("【客户端消息】"+socketMessage.toString());
        log.info("【websocket消息】收到客户端发来的消息"+socketMessage.getType());
        String tokenString=socketMessage.getToken();
        TokenModel tokenModel= TokenUtil.stringToModel(tokenString);
        uid=tokenModel.getUid();
        switch (socketMessage.getType()){
            case "ok": {
                UserDto userDto=JsonUtils.stringToObj(RedisUtil.get(String.valueOf(uid)),UserDto.class);
                List<ChatMessage> chatMessageList= userDto.getChatMessageList();
                Iterator<ChatMessage> chatMessageIterator=chatMessageList.iterator();
                while (chatMessageIterator.hasNext()){
                    if (chatMessageIterator.next().getTimestamp()==Long.parseLong(socketMessage.getContent())){
                        chatMessageIterator.remove();
                    }
                }
                userDto.setChatMessageList(chatMessageList);
                RedisUtil.set(String.valueOf(uid),JsonUtils.objToString(userDto));
                break;
            }
            case "heartbeat":{
                if (!chatSocketMap.containsKey(uid)){
                    chatSocketMap.put(uid,this);
                    UserDto userDto=JsonUtils.stringToObj(RedisUtil.get(String.valueOf(uid)),UserDto.class);
                    List<ChatMessage> offlineMessage=userDto.getChatMessageList();
                    Iterator<ChatMessage> chatMessageIterator=offlineMessage.iterator();
                    ChatMessage m;
                    while (chatMessageIterator.hasNext()) {
                        send(JsonUtils.objToString(chatMessageIterator.next()));

                        chatMessageIterator.remove();
                    }
                    userDto.setChatMessageList(offlineMessage);
                    RedisUtil.set(String.valueOf(uid),JsonUtils.objToString(userDto));
                    log.info("【websocket消息】 有新的连接，总数:"+ chatSocketMap.size());
                    User u=userService.getUserInfoById(uid);
                }
                RedisUtil.set(tokenString,tokenString,10);
                SocketMessage heartbeat=new SocketMessage();
                heartbeat.setType("heartbeat");
                heartbeat.setContent("服务端发给客户端的心跳包");
                try {
                    session.getBasicRemote().sendText(JsonUtils.objToString(heartbeat));
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("发送消息失败");
                }
                break;
            }
            case "friend":{
                chatService.toFriend(socketMessage);
                break;
            }
            case "group":
                chatService.toGroup(socketMessage);
                break;
        }
    }

//    public void sendMessage(String message) {
//        for(ChatSocket chatSocket : chatSocketMao) {
//            System.err.println("【websocket消息】广播消息,message="+message);
//            try {
//                chatSocket.session.getBasicRemote().sendText(message);
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void send(String data){
        try {
            session.getBasicRemote().sendText(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 /*   @Scheduled(fixedRate=5000)
    private void configureTasks() {
        if (RedisUtil.hasKey(this.uid)){
            log.info("用户在线");
        } else {
            System.out.println("get"+this.uid);
            System.out.println(RedisUtil.get(this.uid));
            log.info("用户不在线");
        }
    }

*/
}