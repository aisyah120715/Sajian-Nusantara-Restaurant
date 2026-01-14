package com.sajiannusantara.restaurant_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class RestaurantApiApplication {

	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApiApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		String dbName = mongoTemplate.getDb().getName();
		System.out.println("========================================");
		System.out.println("MongoDB URI: " + mongoUri);
		System.out.println("Connected to Database: " + dbName);
		System.out.println("========================================");
		if (!"food-booking-db".equals(dbName)) {
			System.err.println("ERROR: Connected to wrong database! Expected: food-booking-db, Got: " + dbName);
		}
	}

}
