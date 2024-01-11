package com.tacrolimus.backend;

import com.tacrolimus.backend.config.APIConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Maksim Balashov
 * Основной класс
 */
@SpringBootApplication
public class BackendApplication {
	/**
	 * @param args аргумент
	 */
	public static void main(String[] args) {
		APIConfig.loadEnv();
		SpringApplication.run(BackendApplication.class, args);
	}
}
