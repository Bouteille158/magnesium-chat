package com.sodium.api.models;

public class UserGroup {
    private Integer id;
    private String name;

    public UserGroup(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
