package com.github.dmvegel.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestaurantsApplication {
    static void main(String[] args) {
        SpringApplication.run(RestaurantsApplication.class, args);
    }
}
