package com.example.productssite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class AboutController {

    @ModelAttribute("principal")
    public Principal getPrincipalAttribute(Principal principal) {
        return principal;
    }

    @GetMapping("/about")
    public String aboutSite() {
        return "about";
    }
}
