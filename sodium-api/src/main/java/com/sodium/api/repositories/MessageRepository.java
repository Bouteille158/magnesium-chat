package com.sodium.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sodium.api.models.GroupChat;
import com.sodium.api.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Iterable<Message> findByGroupChat(GroupChat groupChat);

}
