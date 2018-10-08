package co.simplon;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.simplon.services.StorageServices;


@SpringBootApplication/*(scanBasePackages = "co.simplon")*/
public class EverDesignBackApplication {
	@Resource
	StorageServices storageServices;
	
	public static void main(String[] args) {
		SpringApplication.run(EverDesignBackApplication.class, args);
	}
}
