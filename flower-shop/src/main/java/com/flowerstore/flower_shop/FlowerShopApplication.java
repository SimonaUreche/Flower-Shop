package com.flowerstore.flower_shop;

import com.flowerstore.flower_shop.service.MockDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowerShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerShopApplication.class, args);
	}

	@Bean
	CommandLineRunner run(MockDataService mockDataService) {
		return args -> {
			System.out.println("Users:");
			mockDataService.getUsers().forEach(System.out::println);

			System.out.println("Products:");
			mockDataService.getProducts().forEach(System.out::println);

			System.out.println("Orders:");
			mockDataService.getOrders().forEach(System.out::println);
		};
	}
}
