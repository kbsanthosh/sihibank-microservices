package com.sihibank.graphql.config;

import com.sihibank.graphql.model.CustomerCompliance;
import com.sihibank.graphql.repository.CustomerComplianceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Profile("local") // ⚡ CRUCIAL: Runs ONLY on your local machine! Automatically disabled in prod.
public class MongoDataSeeder implements CommandLineRunner {

    private final CustomerComplianceRepository repository;

    public MongoDataSeeder(CustomerComplianceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        // Clear old records first, then batch insert the new records asynchronously
        this.repository.deleteAll()
                .thenMany(Flux.just(
                        new CustomerCompliance(null, "9876543143", "VERIFIED", 12), // Maps to Deepa
                        new CustomerCompliance(null, "9876543954", "COMPLETED", 4)   // Maps to Kushi
                ))
                .flatMap(this.repository::save)
                .subscribe(
                        insertedData -> System.out.println("🚀 MongoDB Seeded Record: " + insertedData),
                        error -> System.err.println("❌ Seeder Error: " + error.getMessage()),
                        () -> System.out.println("✅ Docker MongoDB Initialized with Customer Compliance Profiles!")
                );
    }
}
