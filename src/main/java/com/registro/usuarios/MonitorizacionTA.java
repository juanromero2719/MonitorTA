package com.registro.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.registro.usuarios", "configuracion", "componentes"})
public class MonitorizacionTA {

	public static void main(String[] args) {
		SpringApplication.run(MonitorizacionTA.class, args);
	}

}
