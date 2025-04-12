package com.revie.apartments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RevieApartmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RevieApartmentsApplication.class, args);
    }

}
