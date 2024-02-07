package org.mindswap.springtheknife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringTheKnifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTheKnifeApplication.class, args);
	}

}
