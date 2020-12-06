package com.weeside.desserts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "YES";
    }
}
