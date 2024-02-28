package com.sodium.api;

import org.springframework.web.bind.annotation.RestController;

import com.sodium.api.entities.Message;
import com.sodium.api.repositories.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/messages")
    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

}
