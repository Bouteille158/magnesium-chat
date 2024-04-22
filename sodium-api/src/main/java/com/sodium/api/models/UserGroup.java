package com.sodium.api.models;

public class UserGroup {
    private String name;
    private Integer id;

    public UserGroup(String name, Integer id) {
        this.name = name;
        this.id = id;
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
