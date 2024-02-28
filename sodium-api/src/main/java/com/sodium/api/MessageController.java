package com.sodium.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MessageController {

    @GetMapping("/messages")
    public Message[] getMessages() {
        return new Message[] {
                new Message("Hello, World!", "Alice", "2021-01-01T12:00:00Z"),
                new Message("Hi, there!", "Bob", "2021-01-01T12:01:00Z")
        };
    }

    class Message {
        private String text;
        private String author;
        private String timestamp;

        public Message(String text, String author, String timestamp) {
            this.text = text;
            this.author = author;
            this.timestamp = timestamp;
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
    }

}
