package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class PhoneBookApp {

    public static void main(String[] args) {
        SpringApplication.run(PhoneBookApp.class);
    }
}
