package com.sodium.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sodium.api.models.DBUser;
import com.sodium.api.models.GroupChat;
import com.sodium.api.models.GroupChatCreationRequestBody;
import com.sodium.api.models.GroupChatRequestBody;
import com.sodium.api.models.GroupChatResponse;
import com.sodium.api.repositories.DBUserRepository;
import com.sodium.api.repositories.GroupChatRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class GroupChatController {

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private DBUserRepository userRepository;

    @GetMapping("/api/groups")
    public ResponseEntity<String> getGroups() {
        List<GroupChat> groups = groupChatRepository.findAll();
        StringBuilder groupStringBuilder = new StringBuilder();

        groups.forEach(group -> {
            System.out.println(group.toString());
            groupStringBuilder.append(group.toString() + "\n");
        });

        // TODO : return a list of group chat names instead of string

        return ResponseEntity.ok(groupStringBuilder.toString());
    }

    @PostMapping("/api/addToGroupChat")
    public ResponseEntity<GroupChatResponse> addToGroupChat(@RequestBody GroupChatRequestBody groupChatRequestBody) {
        GroupChat groupChat = groupChatRepository.findById(groupChatRequestBody.getGroupChatId()).get();
        if (groupChat == null) {
            return ResponseEntity.badRequest().body(new GroupChatResponse("Group chat not found", null));
        }

        DBUser user = userRepository.findById(groupChatRequestBody.getUserId()).get();
        if (user == null) {
            return ResponseEntity.badRequest().body(new GroupChatResponse("User not found", null));
        }

        groupChat.addUser(user);

        return ResponseEntity.ok(new GroupChatResponse("User added to group chat", groupChat.getId()));
    }

    @PostMapping("/api/createGroupChat")
    public ResponseEntity<GroupChatResponse> createGroupChat(
            @RequestBody GroupChatCreationRequestBody groupChatCreationRequestBody) {
        String name = groupChatCreationRequestBody.getName();

        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body(new GroupChatResponse("Group chat name is required", null));
        }

        GroupChat groupChat = new GroupChat(name);

        System.out.println(groupChat.toString());

        try {
            groupChatRepository.save(groupChat);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(new GroupChatResponse("Group chat creation failed", null));
        }

        return ResponseEntity.ok(new GroupChatResponse("Group chat created", groupChat.getId()));
    }

}
