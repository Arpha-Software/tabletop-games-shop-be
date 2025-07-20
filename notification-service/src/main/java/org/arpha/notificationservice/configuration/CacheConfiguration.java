package org.arpha.notificationservice.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfiguration {

    @Bean
    @Primary
    public CacheManager cacheManager(@Value("${kyivstar.tokenExpiration}") int expirationHours) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("kyivstarAccessToken");
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(expirationHours, TimeUnit.HOURS)
                        .maximumSize(1)
        );
        return cacheManager;
    }

}
