package com.pdv.oficina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OficinaApplication {

	private static final Logger logger = LoggerFactory.getLogger(OficinaApplication.class);
	public static void main(String[] args) {
		logger.info("Iniciando API da oficina");
		SpringApplication.run(OficinaApplication.class, args);
		logger.info("API da oficina iniciada e pronta para receber requisições");
	}

}
