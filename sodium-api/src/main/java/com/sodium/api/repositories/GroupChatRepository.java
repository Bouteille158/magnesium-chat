package com.sodium.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sodium.api.models.GroupChat;

public interface GroupChatRepository extends JpaRepository<GroupChat, Integer> {

    List<GroupChat> findByUsersId(Long int1);

}