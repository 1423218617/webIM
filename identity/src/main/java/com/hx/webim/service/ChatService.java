package com.hx.webim.service;

import com.hx.webim.model.dto.ChatMessage;

public interface ChatService {
    boolean toFriend(ChatMessage message);
}
