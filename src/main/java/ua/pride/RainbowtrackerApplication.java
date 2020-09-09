package ua.pride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RainbowtrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainbowtrackerApplication.class, args);
    }

}
