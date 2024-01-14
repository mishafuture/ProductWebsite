package com.example.productssite.controllers;

import com.example.productssite.entities.User;
import com.example.productssite.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN_ROLE')")
public class AdminController {
    private final UserService userService;

    @ModelAttribute("principal")
    public Principal getPrincipalAttribute(Principal principal) {
        return principal;
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "admin-panel";
    }

    @PostMapping("user/{id}/changeActive")
    public String changeUserActivity(@PathVariable Long id) {
        userService.changeActivity(id);

        return "redirect:/users";
    }

    @PostMapping("user/{id}/changeRole")
    public String changeUserRole(@PathVariable Long id) {
        userService.changeRole(id);

        return "redirect:/users";
    }
}
