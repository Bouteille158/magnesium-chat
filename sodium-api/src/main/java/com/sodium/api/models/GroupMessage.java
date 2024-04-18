package com.sodium.api.models;

public class GroupMessage {
    private Long id;
    private String text;
    private String author;
    private String timestamp;

    public GroupMessage() {
    }

    public GroupMessage(Message message) {
        this.id = message.getId();
        this.text = message.getText();
        this.author = message.getAuthor();
        this.timestamp = message.getTimestamp();
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

    public void setId(Long id) {
        this.id = id;
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
}