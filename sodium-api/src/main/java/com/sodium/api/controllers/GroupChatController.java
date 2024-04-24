package com.sodium.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sodium.api.models.DBUser;
import com.sodium.api.models.GroupChat;
import com.sodium.api.models.UserGroup;
import com.sodium.api.models.requests.GroupChatCreationRequestBody;
import com.sodium.api.models.requests.GroupChatRequestBody;
import com.sodium.api.models.responses.AddToGroupChatResponse;
import com.sodium.api.models.responses.GroupChatListResponse;
import com.sodium.api.models.responses.GroupChatResponse;
import com.sodium.api.models.responses.UserGroupsResponse;
import com.sodium.api.models.responses.UserResponse;
import com.sodium.api.repositories.DBUserRepository;
import com.sodium.api.repositories.GroupChatRepository;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class GroupChatController {

    @Value("${rsa.private.key}")
    private String jwtKey;

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private DBUserRepository userRepository;

    // TODO: add admin in route
    @GetMapping("/api/groups")
    public ResponseEntity<GroupChatListResponse> getGroups() {
        List<GroupChat> groups = groupChatRepository.findAll();

        GroupChatListResponse groupChatListResponse = new GroupChatListResponse();

        for (GroupChat group : groups) {
            Set<UserResponse> users = new HashSet<>();
            for (DBUser user : group.getUsers()) {
                users.add(new UserResponse(user.getId(), user.getUsername()));
            }
            groupChatListResponse.addGroupChat(new GroupChatResponse(group.getId(), group.getName(), users));
        }

        System.out.println(groupChatListResponse.toString());

        return ResponseEntity.ok(groupChatListResponse);
    }

    @GetMapping("/api/getUserGroups")
    public ResponseEntity<UserGroupsResponse> getUserGroups(@RequestHeader("Authorization") String token) {
        UserGroupsResponse userGroupsResponse = new UserGroupsResponse();

        // Supprimez le préfixe "Bearer " du token
        token = token.replace("Bearer ", "");

        SecretKey secretKey = new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8), "RSA");

        // Récupère l'ID de l'utilisateur à partir du token
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                secretKey)
                .build();

        // Décoder le token
        Jwt jwt = jwtDecoder.decode(token);

        // Récupère l'ID de l'utilisateur à partir du token
        Long userId = jwt.getClaim("userId");

        List<GroupChat> groups = groupChatRepository.findByUsersId(userId);

        for (GroupChat group : groups) {
            userGroupsResponse.addGroupChat(new UserGroup(group.getId(), group.getName()));
        }

        return ResponseEntity.ok(userGroupsResponse);
    }

    @PostMapping("/api/addToGroupChat")
    public ResponseEntity<AddToGroupChatResponse> addToGroupChat(
            @RequestBody GroupChatRequestBody groupChatRequestBody) {
        GroupChat groupChat = groupChatRepository.findById(groupChatRequestBody.getGroupChatId()).get();
        if (groupChat == null) {
            return ResponseEntity.badRequest().body(new AddToGroupChatResponse("Group chat not found", null));
        }

        DBUser user = userRepository.findById(groupChatRequestBody.getUserId()).get();
        if (user == null) {
            return ResponseEntity.badRequest().body(new AddToGroupChatResponse("User not found", null));
        }

        try {
            groupChat.addUser(user);
            groupChatRepository.save(groupChat);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(new AddToGroupChatResponse("User addition failed", null));
        }

        return ResponseEntity.ok(new AddToGroupChatResponse("User added to group chat", groupChat.getId()));
    }

    @PostMapping("/api/createGroupChat")
    public ResponseEntity<AddToGroupChatResponse> createGroupChat(
            @RequestBody GroupChatCreationRequestBody groupChatCreationRequestBody) {
        String name = groupChatCreationRequestBody.getName();

        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body(new AddToGroupChatResponse("Group chat name is required", null));
        }

        GroupChat groupChat = new GroupChat(name);

        System.out.println(groupChat.toString());

        try {
            groupChatRepository.save(groupChat);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(new AddToGroupChatResponse("Group chat creation failed", null));
        }

        return ResponseEntity.ok(new AddToGroupChatResponse("Group chat created", groupChat.getId()));
    }

    @PostMapping("/api/removeFromGroupChat")
    public ResponseEntity<AddToGroupChatResponse> removeFromGroupChat(
            @RequestBody GroupChatRequestBody groupChatRequestBody) {
        GroupChat groupChat = groupChatRepository.findById(groupChatRequestBody.getGroupChatId()).get();
        if (groupChat == null) {
            return ResponseEntity.badRequest().body(new AddToGroupChatResponse("Group chat not found", null));
        }

        DBUser user = userRepository.findById(groupChatRequestBody.getUserId()).get();
        if (user == null) {
            return ResponseEntity.badRequest().body(new AddToGroupChatResponse("User not found", null));
        }

        try {
            groupChat.removeUser(user);
            groupChatRepository.save(groupChat);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest()
                    .body(new AddToGroupChatResponse("User removal failed", groupChat.getId()));
        }

        return ResponseEntity.ok(new AddToGroupChatResponse("User removed from group chat", groupChat.getId()));
    }

}
