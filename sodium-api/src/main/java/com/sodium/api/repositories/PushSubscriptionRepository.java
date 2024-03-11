package com.sodium.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sodium.api.entities.PushSubscription;

public interface PushSubscriptionRepository extends CrudRepository<PushSubscription, Long> {

}
