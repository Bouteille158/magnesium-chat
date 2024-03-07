package com.sodium.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.sodium.api.entities.Message;
import com.sodium.api.repositories.MessageRepository;

@Controller
public class WebSocketController {

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message send(Message message) {
        System.out.println("Message sent: " + message);
        if (message.getId() != null) {
            throw new IllegalArgumentException("You cannot create a message with an ID");
        }
        messageRepository.save(message);
        return message;
    }
}
