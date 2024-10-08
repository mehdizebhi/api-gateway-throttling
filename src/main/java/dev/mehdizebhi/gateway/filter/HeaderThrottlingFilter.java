package dev.mehdizebhi.gateway.filter;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.http.server.reactive.ServerHttpRequest;

public abstract class HeaderThrottlingFilter extends AbstractThrottlingFilter {

    private final String headerName;

    public HeaderThrottlingFilter(ProxyManager<Object> proxyManager, BucketConfigurationResolver bucketConfigurationResolver, String headerName) {
        super(proxyManager, bucketConfigurationResolver);
        this.headerName = headerName;
    }

    @Override
    public String resolveKey(ServerHttpRequest request) {
        return request.getHeaders().getFirst(headerName);
    }
}
