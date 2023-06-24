package com.eaf.gateway.filter;

import com.eaf.gateway.entity.RateLimit;
import com.eaf.gateway.repo.RateLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Date;

@Component
public class RateLimitFilter extends AbstractGatewayFilterFactory<RateLimitFilter.Config> {

    @Autowired
    private RateLimitRepository rateLimitRepository;

    @Autowired
    public RateLimitFilter() {
        super(Config.class);
        //this.rateLimitRepository = rateLimitRepository;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Get user ID and IP address from the request
            Long userId = 101L; // get user ID from the request;
            String ipAddress = "127.0.0.1";// get IP address from the request;

            // Calculate the current date
            Date currentDate = java.sql.Date.valueOf(LocalDate.now());

            System.out.println("rateLimitRepository " + rateLimitRepository);
            // Get the user plan from the request headers
            //String userPlan = exchange.getRequest().getHeaders().getFirst("X-User-Plan");
            //config.setUserPlan(userPlan);

            // Find the rate limit entity in the database
            RateLimit rateLimitEntity = rateLimitRepository
                    .findByUserIdAndIpAddressAndRequestDate(userId, ipAddress, currentDate);

            // If the rate limit entity doesn't exist, create a new one
            if (rateLimitEntity == null) {
                rateLimitEntity = new RateLimit();
                rateLimitEntity.setUserId(userId);
                rateLimitEntity.setIpAddress(ipAddress);
                rateLimitEntity.setRequestDate(currentDate);
                rateLimitEntity.setRequestCount(1);
            } else {
                // If the rate limit entity exists, check if the request count exceeds the limit
                if (rateLimitEntity.getRequestCount() >= config.getRequestLimit()) {
                    // Return an error response or take appropriate action
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                }

                // Increment the request count
                rateLimitEntity.setRequestCount(rateLimitEntity.getRequestCount() + 1);
            }

            // Save the rate limit entity in the database
            rateLimitRepository.save(rateLimitEntity);

            // Proceed with the request
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private String userPlan;

        public int getRequestLimit() {
            // Retrieve the request limit based on user plan
            if ("gold".equalsIgnoreCase(userPlan)) {
                return 20; // Gold plan request limit
            } else if ("silver".equalsIgnoreCase(userPlan)) {
                return 10; // Silver plan request limit
            } else {
                return 1; // Default request limit (fallback value)
            }
        }

        public String getUserPlan() {
            return userPlan;
        }

        public void setUserPlan(String userPlan) {
            this.userPlan = userPlan;
        }
    }

}
