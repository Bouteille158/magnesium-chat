package com.sodium.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sodium.api.models.GroupChat;

public interface GroupChatRepository extends JpaRepository<GroupChat, Integer> {

}