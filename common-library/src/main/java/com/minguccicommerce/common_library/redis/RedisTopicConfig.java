package com.minguccicommerce.common_library.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class RedisTopicConfig {

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("notification:channel");
    }
}