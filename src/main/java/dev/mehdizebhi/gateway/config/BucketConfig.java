package dev.mehdizebhi.gateway.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.caffeine.Bucket4jCaffeine;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {

    @Bean
    public CaffeineProxyManager<Object> caffeineProxyManager(Caffeine<Object, Object> caffeine) {
        return Bucket4jCaffeine.builderFor(caffeine).build();
    }
}