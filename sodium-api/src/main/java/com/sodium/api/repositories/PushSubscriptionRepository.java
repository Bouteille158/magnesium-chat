package com.sodium.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sodium.api.models.PushSubscription;

public interface PushSubscriptionRepository extends CrudRepository<PushSubscription, Long> {
    PushSubscription findByEndpoint(String endpoint);
}
