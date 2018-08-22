package co.simplon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "co.simplon")
public class EverDesignBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EverDesignBackApplication.class, args);
	}
}
