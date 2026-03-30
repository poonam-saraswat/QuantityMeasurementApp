package com.app.quantitymeasurement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Redirects the root URL (/) to Swagger UI so that visiting
 * http://localhost:8080/ shows the API documentation instead of a 500 error.
 */
@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:/swagger-ui.html";
    }
}
