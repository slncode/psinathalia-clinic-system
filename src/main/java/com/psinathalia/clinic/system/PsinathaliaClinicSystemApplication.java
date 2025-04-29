package com.psinathalia.clinic.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PsinathaliaClinicSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsinathaliaClinicSystemApplication.class, args);
	}

}
