package com.sodium.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MessageController {

    @GetMapping("/messages")
    public Message[] getMessages() {
        return new Message[] {
                new Message("Hello, World!", "Alice"),
                new Message("Hi, there!", "Bob")
        };
    }

    class Message {
        private String text;
        private String author;

        public Message(String text, String author) {
            this.text = text;
            this.author = author;
        }

        public String getText() {
            return text;
        }

        public String getAuthor() {
            return author;
        }
    }

}
