package com.sodium.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${aluminium.app.url}")
    private String aluminiumAppUrl;

    @Override
    public void registerStompEndpoints(@SuppressWarnings("null") StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").setAllowedOrigins(aluminiumAppUrl).withSockJS();
    }

    @Override
    public void configureMessageBroker(@SuppressWarnings("null") MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
