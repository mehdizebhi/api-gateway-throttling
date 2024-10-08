package dev.mehdizebhi.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;

public interface KeyResolver {

    String resolveKey(ServerHttpRequest request);
}
