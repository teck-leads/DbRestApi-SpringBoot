package com.fresco.dbrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
public class DbRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbRestApiApplication.class, args);
	}

}
