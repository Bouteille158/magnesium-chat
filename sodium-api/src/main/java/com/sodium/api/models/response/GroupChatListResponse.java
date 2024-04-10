package com.sodium.api.models.response;

import java.util.List;

import com.sodium.api.models.GroupChat;

public class GroupChatListResponse {
    private List<GroupChat> groupChats;

    public GroupChatListResponse(List<GroupChat> groupChats) {
        this.groupChats = groupChats;
    }

    public List<GroupChat> getGroupChats() {
        return groupChats;
    }

    public void setGroupChats(List<GroupChat> groupChats) {
        this.groupChats = groupChats;
    }

    public void addGroupChat(GroupChat groupChat) {
        this.groupChats.add(groupChat);
    }

}
