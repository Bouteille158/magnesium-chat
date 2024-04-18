package com.sodium.api.models.requests;

public class GroupChatRequestBody {
    private Integer groupChatId;
    private Integer userId;

    public GroupChatRequestBody(Integer groupChatId, Integer userId) {
        this.groupChatId = groupChatId;
        this.userId = userId;
    }

    public Integer getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(Integer groupChatId) {
        this.groupChatId = groupChatId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}