package com.lab1.SpringBootLab1.Greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutomacorpApplicationConfig {

    @Bean
    public CommandLineRunner greetingCommandLine(@Autowired GreetingServiceInterface greetingServiceInterface) {
        return args -> {
            greetingServiceInterface.greet("Spring");
        };
    }
}
