package com.personalize.knowledge.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("common")
public interface IdClient {
    @GetMapping("/utils/id")
    String id();
    @GetMapping("/utils/{token}")
    String userId(@PathVariable("token") String token);
}
