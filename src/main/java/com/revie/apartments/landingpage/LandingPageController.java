package com.revie.apartments.landingpage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingPageController {

    @GetMapping("/")
    public String landingPage() {
        return "Welcome to the Revie Apartments API!";
    }

    @GetMapping("/api")
    public String apiInfo() {
        return "This is the Apartments API. Use it to manage your apartment listings.";
    }
}
