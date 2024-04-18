package com.sodium.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sodium.api.entities.Message;
import com.sodium.api.models.GroupChat;
import com.sodium.api.models.request.MessageCreationRequest;
import com.sodium.api.models.requests.MessageCreationRequest;
import com.sodium.api.models.responses.MessageCreationResponse;
import com.sodium.api.repositories.GroupChatRepository;
import com.sodium.api.repositories.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private GroupChatRepository groupChatRepository;

    // TODO: add admin in route
    @GetMapping("/messages")
    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageCreationResponse> createMessage(@RequestBody MessageCreationRequest request) {
        // TODO: check if user is in the chatgroup
        Message message = request.getMessage();
        if (message.getId() != null) {
            throw new IllegalArgumentException("You cannot create a message with an ID");
        }
        GroupChat groupChat = groupChatRepository.findById(request.getChatId()).orElseThrow();
        message.setGroupChat(groupChat);
        message.getChatInfo();

        Message savedMessage = messageRepository.save(message);

        return ResponseEntity.ok(new MessageCreationResponse(savedMessage.getId(), savedMessage.getText(),
                savedMessage.getGroupChat().getId()));
    }

}
