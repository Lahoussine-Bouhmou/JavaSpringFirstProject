package com.lab1.SpringBootLab1.Greeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DummyUserService implements UserService {
    private final GreetingService1 greetingService1;

    @Override
    public void greetAll(List<String> names) {
        for (String name : names) {
            greetingService1.greet(name);
        }
    }
}
