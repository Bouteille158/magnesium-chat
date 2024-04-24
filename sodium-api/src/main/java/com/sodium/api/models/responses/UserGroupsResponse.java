package com.sodium.api.models.responses;

import java.util.HashSet;
import java.util.Set;

import com.sodium.api.models.UserGroup;

public class UserGroupsResponse {
    private Set<UserGroup> userGroups;

    public void addGroupChat(UserGroup userGroup) {
        this.userGroups.add(userGroup);
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public UserGroupsResponse() {
        this.userGroups = new HashSet<>();
    }

    public UserGroupsResponse(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

}
