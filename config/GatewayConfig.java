package com.eaf.gateway.config;

import com.eaf.gateway.filter.RateLimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    RateLimitFilter rateLimitFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        System.out.println(rateLimitFilter);
        return builder.routes()
                .route("swiggy-app", r -> r.path("/swiggy/**")
                        .filters(f -> f.filter(rateLimitFilter.apply(new RateLimitFilter.Config())).addRequestHeader("X-Gold-Plan", "true"))
                        .uri("lb://BACKEND1-SERVICE"))
                .route("payment-app", r -> r.path("/payment/**")
                        .filters(f -> f.filter(rateLimitFilter.apply(new RateLimitFilter.Config())).addRequestHeader("X-Silver-Plan", "true"))
                        .uri("lb://BACKEND2-SERVICE"))
                .build();
    }
}
