package dev.mehdizebhi.gateway.filter;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class IpThrottlingFilter extends AbstractThrottlingFilter {

    public IpThrottlingFilter(ProxyManager<Object> proxyManager, BucketConfigurationResolver bucketConfigurationResolver) {
        super(proxyManager, bucketConfigurationResolver);
    }

    @Override
    public String resolveKey(ServerHttpRequest request) {
        String ip = "unknown";
        if (request.getHeaders().containsKey("X-Forwarded-For")) {
            ip = request.getHeaders().getFirst("X-Forwarded-For").split(",")[0].trim();
        } else if (request.getRemoteAddress() != null) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }
}