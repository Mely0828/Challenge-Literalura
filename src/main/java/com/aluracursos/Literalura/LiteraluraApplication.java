package com.aluracursos.Literalura;

//Importación de recursos
import com.aluracursos.Literalura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.aluracursos.Literalura.repository")
public class LiteraluraApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Autowired
	private Principal principal;

	@Override //Reescritura de función run
	public void run(String... args) throws Exception {
		principal.muestraMenu();
	}
}
