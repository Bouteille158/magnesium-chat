package com.sodium.api.models.responses;

public class AddToGroupChatResponse {
    private String message;
    private Integer groupId;

    public AddToGroupChatResponse(String message, Integer groupId) {
        this.message = message;
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}