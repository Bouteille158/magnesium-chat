package com.sodium.api.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class GroupChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(name = "group_user", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<DBUser> users = new ArrayList<>();

    public GroupChat() {
    }

    public GroupChat(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DBUser> getUsers() {
        return users;
    }

    public void setUsers(List<DBUser> users) {
        this.users = users;
    }

    public void addUser(DBUser user) {
        this.users.add(user);
    }

    public void removeUser(DBUser user) {
        this.users.remove(user);
    }

    @Override
    public String toString() {
        return "GroupChat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void removeUserById(Integer userId) {
        this.users.removeIf(user -> user.getId().equals(userId));
    }

}