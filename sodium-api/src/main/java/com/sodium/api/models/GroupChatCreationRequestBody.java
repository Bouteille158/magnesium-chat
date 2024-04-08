package com.sodium.api.models;

public class GroupChatCreationRequestBody {
    private String name;

    public GroupChatCreationRequestBody(String name) {
        this.name = name;
    }

    public GroupChatCreationRequestBody() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}