package com.example.productssite.controllers;

import com.example.productssite.entities.User;
import com.example.productssite.services.UserProductService;
import com.example.productssite.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ModelAttribute("principal")
    public Principal getPrincipalAttribute(Principal principal) {
        return principal;
    }

    @GetMapping("/user/{id}/edit")
    public String editionUserInfo(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        return "account";
    }

    @PostMapping("/user/{id}/edit")
    public String editUserInfo(@Valid User user, BindingResult bindingResult,
                               @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "account";
        }

        userService.editWithoutPassword(user);

        return "redirect:/products";
    }

    @GetMapping("/user/{email}")
    public String redirectToAccount(@PathVariable String email) {
        User user = userService.getUserByEmail(email);

        return "redirect:/user/" + user.getId() + "/edit";
    }
}
