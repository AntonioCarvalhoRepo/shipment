package xyz.shipments.financial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShipmentsApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShipmentsApplication.class, args);
	}
}
