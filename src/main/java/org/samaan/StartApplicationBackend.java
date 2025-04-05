package org.samaan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "org.samaan.repositories")
@SpringBootApplication
public class StartApplicationBackend {
    public static void main(String[] args) {
        SpringApplication.run(StartApplicationBackend.class, args);
    }
}