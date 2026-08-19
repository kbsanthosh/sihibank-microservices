package com.sihibank.graphql.repository;

import com.sihibank.graphql.model.CustomerCompliance;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerComplianceRepository extends ReactiveMongoRepository<CustomerCompliance, String> {
    // Custom non-blocking lookup method to search by phone number
    Mono<CustomerCompliance> findByMobileNumber(String mobileNumber);
}
