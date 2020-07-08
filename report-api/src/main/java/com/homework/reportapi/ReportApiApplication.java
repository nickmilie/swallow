package com.homework.reportapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReportApiApplication/* extends SpringBootServletInitializer */{

	public static void main(String[] args) {
		SpringApplication.run(ReportApiApplication.class, args);
	}

	/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ReportApiApplication.class);
	}*/
}
