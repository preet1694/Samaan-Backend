package org.samaan.config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(new ConnectionString("mongodb+srv://vrajranipa7:vraj1204@cluster0.lreyi.mongodb.net/samaan"));
    }
}
