package com.sodium.api;

import nl.martijndwars.webpush.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sodium.api.entities.PushSubscription;
import com.sodium.api.repositories.PushSubscriptionRepository;

import java.security.GeneralSecurityException;
import java.security.Security;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class PushNotificationController {

    @Autowired
    private PushSubscriptionRepository pushSubscriptionRepository;

    @Value("${vapid.public.key}")
    private String vapidPublicKeyString;

    @Value("${vapid.private.key}")
    private String vapidPivateKeyString;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @GetMapping("/api/vapidPublicKey")
    public String getVapidPublicKey() {
        return vapidPublicKeyString;
    }

    @GetMapping("/api/getSubscriptionsList")
    public Iterable<PushSubscription> getSubscriptionsList() {
        Iterable<PushSubscription> subs = pushSubscriptionRepository.findAll();
        System.out.println("Subscriptions: " + subs.toString());
        return subs;
    }

    @PostMapping("/api/push")
    public void sendPushNotification(@RequestBody String payload)
            throws GeneralSecurityException, JoseException, InterruptedException {

        System.out.println("Payload: " + payload.trim());
        PushService pushService = new PushService();
        pushService.setPublicKey(vapidPublicKeyString);
        pushService.setPrivateKey(vapidPivateKeyString);

        Iterable<PushSubscription> pushSubscriptions = pushSubscriptionRepository.findAll();
        System.out.println("Sending notification to " + pushSubscriptions.toString());
        for (PushSubscription pushSubscription : pushSubscriptions) {
            System.out.println("Sending notification to " + pushSubscription.getEndpoint());
            Subscription subscription = new Subscription(
                    pushSubscription.getEndpoint(),
                    new Subscription.Keys(pushSubscription.getKeys().getP256dh(),
                            pushSubscription.getKeys().getAuth()));

            Notification notification = new Notification(
                    subscription,
                    payload);

            System.out.println("Notification: " + notification.toString());

            try {
                pushService.send(notification);
                System.out.println("Notification sent successfully to " + pushSubscription.getEndpoint());
            } catch (Exception e) {
                System.out.println("Failed to send notification to " + pushSubscription.getEndpoint());
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/api/subscribeToPushNotification")
    public ResponseEntity<String> subscribeToPushNotification(@RequestBody PushSubscription subscription) {
        System.out.println("Subscription: " + subscription.toString());
        pushSubscriptionRepository.save(subscription);
        return ResponseEntity.ok("Subscription registered successfully");
    }

}