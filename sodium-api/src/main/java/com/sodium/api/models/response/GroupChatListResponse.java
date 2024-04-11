package com.sodium.api.models.response;

import java.util.ArrayList;
import java.util.List;

public class GroupChatListResponse {
    private List<GroupChatResponse> groupChats;

    public GroupChatListResponse() {
        this.groupChats = new ArrayList<>();
    }

    public GroupChatListResponse(List<GroupChatResponse> groupChats) {
        this.groupChats = groupChats;
    }

    public List<GroupChatResponse> getGroupChats() {
        return groupChats;
    }

    public void setGroupChats(List<GroupChatResponse> groupChats) {
        this.groupChats = groupChats;
    }

    public void addGroupChat(GroupChatResponse groupChat) {
        this.groupChats.add(groupChat);
    }

}
