package dev.mehdizebhi.gateway.filter;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public abstract class AbstractThrottlingFilter implements GatewayFilter, KeyResolver {

    private final ProxyManager<Object> proxyManager;
    private final BucketConfigurationResolver bucketConfigurationResolver;

    public AbstractThrottlingFilter(ProxyManager<Object> proxyManager, BucketConfigurationResolver bucketConfigurationResolver) {
        this.proxyManager = proxyManager;
        this.bucketConfigurationResolver = bucketConfigurationResolver;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var bucket = proxyManager.getProxy(resolveKey(exchange.getRequest()), () -> bucketConfigurationResolver.getBucketConfiguration(exchange.getRequest()));
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String body = "{\"status\": 429, \"message\": \"Too many requests\"}";
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
    }
}
