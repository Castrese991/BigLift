package com.big_lift.palestra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableWebSecurity
public class BigliftPalestraApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigliftPalestraApplication.class, args);
	}

}
