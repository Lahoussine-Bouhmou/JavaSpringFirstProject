package com.lab1.SpringBootLab1.Greeting;

import org.springframework.stereotype.Service;

@Service
public class GreetingService1 implements GreetingServiceInterface {
    public void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }
}
