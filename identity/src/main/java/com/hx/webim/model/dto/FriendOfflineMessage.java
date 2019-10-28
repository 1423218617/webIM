package com.hx.webim.model.dto;

import java.util.List;

public class FriendOfflineMessage {

    private List<ChatMessage> messageList;

    public List<ChatMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }
}
