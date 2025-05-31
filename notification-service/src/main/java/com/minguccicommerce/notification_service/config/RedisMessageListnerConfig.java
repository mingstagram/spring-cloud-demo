package com.minguccicommerce.notification_service.config;

import com.minguccicommerce.notification_service.listener.NotificationSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
public class RedisMessageListnerConfig {

    private final NotificationSubscriber notificationSubscriber;

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener) notificationSubscriber, topic());
        return container;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("notification:channel");
    }

}
