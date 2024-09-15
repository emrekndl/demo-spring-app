package com.example.demo.finalexam;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setAllowNullValues(false);

        cacheManager.setCacheNames(Arrays.asList("products_cache"));
        return cacheManager;
    }
    
    @CacheEvict(cacheNames = "products_cache", allEntries = true)
    @Scheduled(fixedDelay = 500000, initialDelay = 0) // 5 minutes
    public void clearCache() {
        System.out.println("Cleared cache");
    }
}
