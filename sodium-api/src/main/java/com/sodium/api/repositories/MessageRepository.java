package com.sodium.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sodium.api.entities.Message;
import com.sodium.api.models.GroupChat;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Iterable<Message> findByGroupChat(GroupChat groupChat);

}
