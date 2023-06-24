package com.eaf.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimit {

    @Id
    @Column(length = 50)
    private String routeId;

    private int limitForMinutes;
    private int requestCount;
    private LocalDateTime lastRequestTimestamp;

}
