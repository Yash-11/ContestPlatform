package com.example.contestplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ContestplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContestplatformApplication.class, args);
	}

}
