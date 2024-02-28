package com.sodium.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sodium.api.entities.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
