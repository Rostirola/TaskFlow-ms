package com.example.tarefaservice.service.feign;

import com.example.tarefaservice.model.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("LOG-SERVICE")
public interface LogClient {
    @PostMapping("/")
    void registrarLog(@RequestBody Log log);
}