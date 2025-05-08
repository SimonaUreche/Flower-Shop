package com.flowerstore.flower_shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // setam un in-memory message broker care asteapta mesaje de la destinatii cu prefixul /topic
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // inregirstram un endpoint la /socket unde se pot conecta clientii
        // permitem inregistrarea clientilor de la aplicatie client care ruleaza la localhost:300
        stompEndpointRegistry.addEndpoint("/socket")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }


}