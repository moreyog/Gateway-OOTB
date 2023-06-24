package com.eaf.gateway;

import com.eaf.gateway.filter.RateLimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.eaf.gateway")
public class GatewayApplication {


	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}



//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route("gold_plan_route", r -> r.path("/api/gold/**")
//						.filters(f -> f.filter(rateLimitFilter).addRequestHeader("X-Gold-Plan", "true"))
//						.uri("http://your-service-for-gold-plan"))
//				.route("silver_plan_route", r -> r.path("/api/silver/**")
//						.filters(f -> f.filter(rateLimitFilter).addRequestHeader("X-Silver-Plan", "true"))
//						.uri("http://your-service-for-silver-plan"))
//				.build();
//	}
}
