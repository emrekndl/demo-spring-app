package com.example.demospringapp;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfiguration {

    @Bean
    // bean -> gets injected into the spring container using dependency injection
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager();
        manager.setAllowNullValues(false); 
        manager.setCacheNames(Arrays.asList("productCache"));
        return manager;
    }
    
    @CacheEvict(cacheNames = "productCache", allEntries = true)
    @Scheduled(fixedDelay = 7000, initialDelay = 0)
    // every 7 seconds after clearing the cache
    public void evictProductCache() {
        System.out.println("Evicting product cache");
    }
}
