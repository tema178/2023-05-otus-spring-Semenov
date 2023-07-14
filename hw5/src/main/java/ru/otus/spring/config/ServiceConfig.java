package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.IOServiceStreams;

@Configuration
@SuppressWarnings("unused")
public class ServiceConfig {

    @Bean
    public IOServiceStreams printService() {
        return new IOServiceStreams(System.out, System.in);
    }
}
