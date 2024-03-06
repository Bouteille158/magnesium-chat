package com.sodium.api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.sodium.api.entities.Message;

@Controller
public class WebSocketController {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message send(Message message) {
        System.out.println("Message sent: " + message);
        // TODO: Save message to database
        return message;
    }
}
