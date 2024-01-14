package com.example.productssite.controllers;

import com.example.productssite.entities.User;
import com.example.productssite.services.AuthService;
import com.example.productssite.services.UserService;
import com.example.productssite.services.impl.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @ModelAttribute("principal")
    public Principal getPrincipalAttribute(Principal principal) {
        return principal;
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {

        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid User user,
                           BindingResult bindingResult) {
        if (userService.isEmailUnique(user.getEmail())) {
            bindingResult.rejectValue("email", "user.email",
                    "This email is already in use");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        String password = user.getPassword();

        userService.save(user);
        authService.doAutoLogin(user.getUsername(), password);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("anonymousUser".equals(authentication.getPrincipal()));

        return "redirect:/products";
    }


    @GetMapping("/login")
    public String login() {

        return "login";
    }

/*    private void autoLogin(User user) {
        // Автоматическая аутентификация пользователя (надо запомнить)
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, user.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authenticationToken));
    }*/
}
