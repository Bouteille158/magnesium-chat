package com.sodium.api.models;

import java.util.HashSet;
import java.util.Set;

import com.sodium.api.entities.Message;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "groupchat_user", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<DBUser> users = new HashSet<>();

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

    public Set<DBUser> getUsers() {
        return users;
    }

    public void setUsers(Set<DBUser> users) {
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