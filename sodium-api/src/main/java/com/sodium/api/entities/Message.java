package com.sodium.api.entities;

import java.time.Instant;

import com.sodium.api.models.GroupChat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    private String author;
    private String timestamp;

    @OneToOne
    @JoinColumn(name = "groupChat")
    private GroupChat groupChat;

    // Default constructor
    public Message() {
    }

    public Message(String text, String author, String timestamp, GroupChat groupChat) {
        this.text = text;
        this.author = author;
        this.timestamp = Instant.now().toString();
        this.groupChat = groupChat;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public void setGroupChat(GroupChat groupChat) {
        this.groupChat = groupChat;
    }

    public GroupChat getGroupChat() {
        return groupChat;
    }

    public String getChatName() {
        return groupChat.getName();
    }

    public Integer getChatId() {
        return groupChat.getId();
    }

    public String getChatInfo() {
        return "Chat ID: " + groupChat.getId() + "\n" +
                "Chat Name: " + groupChat.getName();
    }

}