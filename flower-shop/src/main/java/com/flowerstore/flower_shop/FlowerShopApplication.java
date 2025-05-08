package com.flowerstore.flower_shop;

import com.flowerstore.flower_shop.constants.UserType;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowerShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerShopApplication.class, args);
	}
//	@Bean
//	CommandLineRunner init(UserRepository userRepository) {
//		return args -> {
//			if (userRepository.findById(0L).isEmpty()) {
//				User guest = User.builder()
//						.id(0L)
//						.name("Guest")
//						.email("guest@flowerstore.com")
//						.password("")
//						.role(UserType.CLIENT)
//						.build();
//
//				// Dezactivezi temporar IDENTITY pentru a seta ID 0 dacă suportă DB-ul
//				userRepository.save(guest);
//			}
//		};
//	}

}
