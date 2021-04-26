package com.iqmsoft.boot.batch.integration;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class SpringBootBatchIntegrate {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringBootBatchIntegrate.class)//
				.web(WebApplicationType.NONE)//
				.run(args);
	}

}
