package com.epam.esm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class ApiApplication implements CommandLineRunner {
    private final Generator generator;

    public ApiApplication(Generator generator) {
        this.generator = generator;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Thread.sleep(1000);
//        generator.save1000Tags();
//        Thread.sleep(1000);
//        generator.save1000Users();
//        Thread.sleep(1000);
//        generator.save10000Gifts();
//        Thread.sleep(1000);
//        generator.save1000Orders();
    }
}