package com.dxc.mts.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 
 * @author mkhan339
 *
 */
@CrossOrigin(origins = "http://localhost:4200/**")
@SpringBootApplication
public class MoneyTransferSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferSystemApplication.class, args);
	}

}
