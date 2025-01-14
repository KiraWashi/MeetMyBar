package org.meetmybar.meetmybarapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.meetmybar"})
public class MeetMyBarApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetMyBarApiApplication.class, args);
	}

}
