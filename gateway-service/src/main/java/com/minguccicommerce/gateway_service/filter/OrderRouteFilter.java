package com.minguccicommerce.gateway_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class OrderRouteFilter extends AbstractGatewayFilterFactory<Object> {

    public OrderRouteFilter() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            log.info("[ORDER ROUTE FILTER] >>> 주문 서비스 호출: {}", exchange.getRequest().getPath());
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    log.info("[ORDER ROUTE FILTER] <<< 응답 상태: {}", exchange.getResponse().getStatusCode())));
        };
    }
}