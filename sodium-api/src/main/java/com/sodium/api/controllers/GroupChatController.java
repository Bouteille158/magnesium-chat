package com.sodium.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sodium.api.models.DBUser;
import com.sodium.api.models.GroupChat;
import com.sodium.api.models.request.GroupChatCreationRequestBody;
import com.sodium.api.models.request.GroupChatRequestBody;
import com.sodium.api.models.response.GroupChatListResponse;
import com.sodium.api.models.response.GroupChatResponse;
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

    // TODO: add admin in route
    @GetMapping("/api/groups")
    public ResponseEntity<GroupChatListResponse> getGroups() {
        List<GroupChat> groups = groupChatRepository.findAll();
        GroupChatListResponse groupChatListResponse = new GroupChatListResponse(groups);

        // TODO: remove passwords and personnal info from response

        return ResponseEntity.ok(groupChatListResponse);
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

        try {
            groupChat.addUser(user);
            groupChatRepository.save(groupChat);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(new GroupChatResponse("User addition failed", null));
        }

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
