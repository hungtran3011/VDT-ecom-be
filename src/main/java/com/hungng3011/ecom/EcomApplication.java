package com.hungng3011.ecom;

//import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class EcomApplication {
    public static void main(String[] args) {
        // Load .env file before Spring Boot starts
//        Dotenv.configure().ignoreIfMissing().load();
        SpringApplication.run(EcomApplication.class, args);
    }
}
