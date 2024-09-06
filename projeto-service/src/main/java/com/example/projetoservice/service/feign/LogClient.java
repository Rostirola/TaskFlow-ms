package com.example.projetoservice.service.feign;

import com.example.projetoservice.model.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("LOG-SERVICE")
public interface LogClient {
    @PostMapping("/")
    void registrarLog(@RequestBody Log log);
}