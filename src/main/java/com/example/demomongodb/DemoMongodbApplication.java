package com.example.demomongodb;

import com.example.demomongodb.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@AllArgsConstructor
public class DemoMongodbApplication {

    private final PaymentService paymentService;

    public static void main(String[] args) {
        SpringApplication.run(DemoMongodbApplication.class, args);
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            paymentService.createWallet("Mafei",1000.00).subscribe();
            paymentService.createWallet("Sahim",2000.00).subscribe();
        };
    }
}
