package dev.mehdizebhi.gateway.filter;

import io.github.bucket4j.BucketConfiguration;
import org.springframework.http.server.reactive.ServerHttpRequest;

@FunctionalInterface
public interface BucketConfigurationResolver {

    BucketConfiguration getBucketConfiguration(ServerHttpRequest request);
}
