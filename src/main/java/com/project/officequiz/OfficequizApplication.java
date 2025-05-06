package com.project.officequiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OfficequizApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficequizApplication.class, args);
	}

}
