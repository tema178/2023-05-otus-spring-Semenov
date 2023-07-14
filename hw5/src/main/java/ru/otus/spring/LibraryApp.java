package ru.otus.spring;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LibraryApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LibraryApp.class);
        Console.main(args);
    }
}
