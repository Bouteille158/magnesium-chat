package com.sodium.api.models.response;

public class GroupChatResponse {
    private String message;
    private Integer groupId;

    public GroupChatResponse(String message, Integer groupId) {
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