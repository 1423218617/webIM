package com.hx.webim.service;

import com.hx.webim.chatControlller.ChatSocket;
import com.hx.webim.model.dto.SocketMessage;

public interface ChatService {
    boolean toFriend( SocketMessage message);
}
