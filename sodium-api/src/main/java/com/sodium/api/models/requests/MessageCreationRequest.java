package com.sodium.api.models.requests;

import com.sodium.api.entities.Message;

public class MessageCreationRequest {
    private Message message;
    private Integer chatId;

    public MessageCreationRequest(Message message, Integer chatId) {
        this.message = message;
        this.chatId = chatId;
    }

    public Message getMessage() {
        return message;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

}
