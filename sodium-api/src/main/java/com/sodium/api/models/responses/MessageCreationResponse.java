package com.sodium.api.models.responses;

public class MessageCreationResponse {
    private Long messageId;
    private String message;
    private Integer chatId;

    public MessageCreationResponse(Long messageId, String message, Integer chatId) {
        this.messageId = messageId;
        this.message = message;
        this.chatId = chatId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }
}
