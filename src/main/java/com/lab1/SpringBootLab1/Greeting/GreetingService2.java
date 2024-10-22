package com.lab1.SpringBootLab1.Greeting;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class GreetingService2 implements GreetingServiceInterface {

    public void greet(String name) {
        System.out.println("Bonjour, " + name + "!");
    }
}
