package com.hungng3011.ecom;

import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/api/v1")
    public String index() {
        return "Hello, World!";
    }
}
