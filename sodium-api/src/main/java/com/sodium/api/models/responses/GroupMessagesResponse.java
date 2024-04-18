package com.sodium.api.models.responses;

import java.util.ArrayList;
import java.util.List;

import com.sodium.api.models.GroupMessage;

public class GroupMessagesResponse {
    private List<GroupMessage> messages;

    public GroupMessagesResponse() {
        this.messages = new ArrayList<>();
    }

    public List<GroupMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<GroupMessage> messages) {
        this.messages = messages;
    }

    public void addMessage(GroupMessage message) {
        this.messages.add(message);
    }
}