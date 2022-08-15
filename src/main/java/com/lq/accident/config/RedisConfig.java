package com.lq.accident.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@Slf4j
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory getRedisConnectionFactory(RedisProperties redisProperties){
//        LettuceConnectionFactory factory = new LettuceConnectionFactory("120.48.105.135",9763);
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisProperties.getHost(),redisProperties.getPort());
        if (StrUtil.isNotEmpty(redisProperties.getPassword()))
            configuration.setPassword(redisProperties.getPassword());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);

        return factory;
    }

    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
