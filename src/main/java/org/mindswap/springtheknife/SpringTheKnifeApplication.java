package org.mindswap.springtheknife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringTheKnifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTheKnifeApplication.class, args);
    }

}
