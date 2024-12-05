package com.big_lift.palestra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class BigliftPalestraApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigliftPalestraApplication.class, args);
	}

}
