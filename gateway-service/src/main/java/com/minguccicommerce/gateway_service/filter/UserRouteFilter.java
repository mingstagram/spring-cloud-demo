package com.minguccicommerce.gateway_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserRouteFilter extends AbstractGatewayFilterFactory<Object> {

    public UserRouteFilter() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            log.info("[USER ROUTE FILTER] 요청 경로: {}", exchange.getRequest().getPath());
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    log.info("[USER ROUTE FILTER] 응답 상태: {}", exchange.getResponse().getStatusCode())));
        };
    }
}