package com.sodium.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sodium.api.models.GroupChat;
import com.sodium.api.models.GroupMessage;
import com.sodium.api.models.Message;
import com.sodium.api.models.exceptions.ItemNotFoundException;
import com.sodium.api.models.requests.MessageCreationRequest;
import com.sodium.api.models.responses.GroupMessagesResponse;
import com.sodium.api.models.responses.MessageCreationResponse;
import com.sodium.api.repositories.GroupChatRepository;
import com.sodium.api.repositories.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/groupMessages/{groupId}")
    public ResponseEntity<GroupMessagesResponse> getMessagesByGroup(@PathVariable Integer groupId) {
        // TODO check if user is in the chatgroup
        GroupChat groupChat = groupChatRepository.findById(groupId).orElseThrow(() -> new ItemNotFoundException(
                String.format("Group chat with id %d not found", groupId)));

        Iterable<Message> messagesFromGroup = messageRepository.findByGroupChat(groupChat);

        GroupMessagesResponse groupMessagesResponse = new GroupMessagesResponse();

        for (Message message : messagesFromGroup) {
            groupMessagesResponse.addMessage(
                    new GroupMessage(message));
        }

        return ResponseEntity.ok(groupMessagesResponse);
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
