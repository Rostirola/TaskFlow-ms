package com.example.projetoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjetoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoServiceApplication.class, args);
    }

}
