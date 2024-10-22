package com.lab1.SpringBootLab1;

import com.lab1.SpringBootLab1.Greeting.GreetingService1;
import com.lab1.SpringBootLab1.Greeting.DummyUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.List;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
class SpringBootApplicationTests {

	@Autowired
	private GreetingService1 greetingService;

	@Autowired
	private DummyUserService dummyUserService;

	@Test
	public void testGreeting(CapturedOutput output) {
		greetingService.greet("Hamza");
		Assertions.assertThat(output.getOut()).contains("Hello, Hamza!");
	}

	@Test
	public void testGreetingAll(CapturedOutput output) {
		dummyUserService.greetAll(List.of("Elodie", "Charles"));
		Assertions.assertThat(output).contains("Hello, Elodie!", "Hello, Charles!");
	}
}
