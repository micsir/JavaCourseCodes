package io.github.kimmking.gateway.router;

import java.util.List;

@FunctionalInterface
public interface HttpEndpointRouter {
    String route(List<String> endpoints,List<String> proxyServers);
}
