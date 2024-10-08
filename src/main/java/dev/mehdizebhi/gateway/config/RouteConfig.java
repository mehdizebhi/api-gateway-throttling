package dev.mehdizebhi.gateway.config;

import dev.mehdizebhi.gateway.filter.IpThrottlingFilter;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RouteConfig {

    @Autowired
    private ProxyManager<Object> proxyManager;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("test", r -> r.path("/test")
                        .filters(f -> f
                                .filter(
                                        new IpThrottlingFilter(proxyManager, (request) -> BucketConfiguration.builder()
                                                .addLimit(limit -> limit.capacity(2).refillIntervally(1, Duration.ofSeconds(60)))
                                                .build()
                                        ))
                        )
                        .uri("https://www.google.com/"))
                .build();
    }
}