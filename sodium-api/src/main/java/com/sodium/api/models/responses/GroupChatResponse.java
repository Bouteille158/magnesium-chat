package com.sodium.api.models.responses;

import java.util.Set;

public class GroupChatResponse {
    private Integer id;
    private String name;
    private Set<UserResponse> users;

    public GroupChatResponse(Integer id, String name, Set<UserResponse> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<UserResponse> getUsers() {
        return users;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(Set<UserResponse> users) {
        this.users = users;
    }

    public void addUser(UserResponse user) {
        this.users.add(user);
    }

}