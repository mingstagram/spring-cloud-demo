package com.minguccicommerce.gateway_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        log.info("[GLOBAL FILTER] Incoming request path: {}", path);

        return chain.filter(exchange).then(
                Mono.fromRunnable(() ->
                        log.info("[GLOBAL FILTER] Outgoing response status: {}", exchange.getResponse().getStatusCode()))
        );
    }

    @Override
    public int getOrder() {
        return -1; // 먼저 실행되도록 우선순위 조절 가능
    }
}
