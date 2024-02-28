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
        private String message;
        private String author;

        public Message(String message, String author) {
            this.message = message;
            this.author = author;
        }

        public String getMessage() {
            return message;
        }

        public String getAuthor() {
            return author;
        }
    }

}
