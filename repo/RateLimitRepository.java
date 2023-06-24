package com.eaf.gateway.repo;

import com.eaf.gateway.entity.RateLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateLimitRepository extends JpaRepository<RateLimit, String> {

    Optional<RateLimit> findByRouteId(String routeId);
}
