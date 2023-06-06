package com.arango.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.arango.auction.repository")
public class AuctionSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuctionSystemApplication.class, args);
	}

}
