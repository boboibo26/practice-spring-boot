package com.api.API.database;

import com.api.API.model.Product;
import com.api.API.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class Database {
    private static final Logger log = Logger.getLogger(Database.class.getName());
    @Bean
    CommandLineRunner init(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product productA = new Product("Milk", 200);
                Product productB = new Product("Rice", 400);
                log.info("insert data: " + productRepository.save(productA));
                log.info("insert data: " + productRepository.save(productB));
            }
        };
    }
}
